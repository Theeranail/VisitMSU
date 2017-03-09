package com.example.visitmsu.visitmsu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.visitmsu.visitmsu.model.CheckAccessInternet;
import com.example.visitmsu.visitmsu.model.Config;
import com.example.visitmsu.visitmsu.model.CreateFuction;
import com.example.visitmsu.visitmsu.model.JsonHttp;
import com.example.visitmsu.visitmsu.model.Msu;
import com.example.visitmsu.visitmsu.model.SharedPreferencesCheck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public SharedPreferencesCheck spc;
    private CheckAccessInternet cAn;
    public String Name_building,
            email_building,
            Image_mains,
            Tel_building,
            latitude_building,
            longitude_building,
            detail_building,
            address_building,
            id_type_building;
    public int id_building;
    public ListView listViewdetail;
    public TextView text_name_main, text_email, text_tel, text_address, textvew_nodata, textview_detail;
    public ImageView img_main;
    public ProgressBar progressBarStyleLarge;
    public List<Msu.listSubbuilding> listSubbuildings;
    public Button btn_viewlistdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spc = new SharedPreferencesCheck(DetailActivity.this);
        spc.checksharedPre();

        Bundle bundle = getIntent().getExtras();
        Name_building = bundle.getString("Name_building");
        id_building = bundle.getInt("id_building");
        Image_mains = bundle.getString("Image_main");
        latitude_building = bundle.getString("latitude_building");
        longitude_building = bundle.getString("longitude_building");
        Tel_building = bundle.getString("Tel_building");
        detail_building = bundle.getString("detail_building");
        address_building = bundle.getString("address_building");
        id_type_building = bundle.getString("id_type_building");
        email_building = bundle.getString("email_building");

        text_name_main = (TextView) findViewById(R.id.text_name_main);
        text_email = (TextView) findViewById(R.id.text_email);
        text_tel = (TextView) findViewById(R.id.text_tel);
        text_address = (TextView) findViewById(R.id.text_address);
        img_main = (ImageView) findViewById(R.id.img_main);
        //  listViewdetail = (ListView) findViewById(R.id.listetail);
        textvew_nodata = (TextView) findViewById(R.id.textvew_nodata);
        progressBarStyleLarge = (ProgressBar) findViewById(R.id.progressBar);
        textview_detail = (TextView) findViewById(R.id.text_detail);
        btn_viewlistdata = (Button) findViewById(R.id.btn_viewlistdata);


        getSupportActionBar().setTitle("รายละเอียด " + Name_building);

        //   Log.e("Image_mains",Image_mains);
        Glide.with(DetailActivity.this)
                .load(Image_mains)
                .placeholder(R.drawable.ic_wallpaper_black_24dp)
                .error(R.drawable.ic_wallpaper_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img_main);

        text_name_main.setText(" " + Name_building);
        if (email_building != null && !email_building.isEmpty() && !email_building.equals("null")) {
            text_email.setText(" " + email_building);
        } else {
            text_email.setText(" " + "ไม่มีข้อมูล");
        }
        if (Tel_building != null && !Tel_building.isEmpty() && !Tel_building.equals("null")) {
            text_tel.setText(" " + Tel_building);
        } else {
            text_tel.setText(" " + "ไม่มีข้อมูล");
        }
        if (address_building != null && !address_building.isEmpty() && !address_building.equals("null")) {
            text_address.setText(" " + address_building);
        } else {
            text_address.setText(" " + "ไม่มีข้อมูล");
        }
        if (detail_building != null && !detail_building.isEmpty() && !detail_building.equals("null")) {
            textview_detail.setText(" " + detail_building);
        } else {
            textview_detail.setText(" " + "");
        }

        cAn = new CheckAccessInternet(DetailActivity.this);
        if (cAn.isConnectNet()) {
            new LoadDetaSubbuilding(getApplicationContext(), id_building).execute();
        } else {
            Toast.makeText(DetailActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
        }

        text_tel.setOnClickListener(this);
        text_email.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SharedPreferencesCheck.Logout();
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.putExtra("search_", true);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void DialogListSubbuilding() {

        final AlertDialog.Builder dai_builder = new AlertDialog.Builder(DetailActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.content_listview_sub, null);

        listViewdetail = (ListView) view.findViewById(R.id.listetail);

        dai_builder.setTitle("รายการ หน่วยงาน/ผู้ประกอบการ");
        dai_builder.setView(view);

        dai_builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog ad = dai_builder.show();

        listViewdetail.setAdapter(new CreateViewDetail(DetailActivity.this, listSubbuildings));
        listViewdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowdetailSubbuilding(i);
            }
        });

        listViewdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ad.dismiss();
                ShowdetailSubbuilding(position);
                //setFocusMarker(Double.valueOf(listVansearch.get(position).getLattitude()).doubleValue(), Double.valueOf(listVansearch.get(position).getLogtitude()).doubleValue());
            }
        });
    }

    public void ShowdetailSubbuilding(int position) {

        Intent intent = new Intent(DetailActivity.this, DetailSubActivity.class);
        intent.putExtra("Id_sub_building", listSubbuildings.get(position).getId_sub_building());
        intent.putExtra("id_building", listSubbuildings.get(position).getId_building());
        intent.putExtra("name_sub_duilding", listSubbuildings.get(position).getName_sub_building());
        intent.putExtra("Tel_sub_building", listSubbuildings.get(position).getTel_sub_building());
        intent.putExtra("email_building", listSubbuildings.get(position).getEmail_sub_building());
        intent.putExtra("Image_main", listSubbuildings.get(position).getImage_main_sub());
        intent.putExtra("detail_building", listSubbuildings.get(position).getDetail_sub_building());
        intent.putExtra("address_building", listSubbuildings.get(position).getAddress_sub_building());

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_tel:
                if (Tel_building != null && !Tel_building.isEmpty() && !Tel_building.equals("null")) {
                    CreateFuction createFuction = new CreateFuction(DetailActivity.this);
                    createFuction.DialogAlertPhone(Tel_building);
                }
                break;
            case R.id.text_email:
                if (email_building != null && !email_building.isEmpty() && !email_building.equals("null")) {
                    CreateFuction createFuction = new CreateFuction(DetailActivity.this);
                    createFuction.DialogAlertSendmail(email_building, Name_building);
                }
                break;
        }
    }


    public class LoadDetaSubbuilding extends AsyncTask<Object, Integer, List<Msu.listSubbuilding>> {
        private Context context;
        private int id_building;
        private ProgressDialog progress;

        public LoadDetaSubbuilding(Context context, int id_building) {
            this.context = context;
            this.id_building = id_building;
        }

        @Override
        protected void onPreExecute() {
            if (progressBarStyleLarge.getVisibility() == View.GONE) {
                progressBarStyleLarge.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<Msu.listSubbuilding> doInBackground(Object... objects) {
            try {
                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();
                Log.e("id_car", String.valueOf(id_building).toString());
                RequestBody formbody = new FormBody.Builder()
                        .add("function", "getSubbuilding")
                        .add("id_building", String.valueOf(id_building).toString())
                        .build();
                String ressult = jsonHttp.Postdata(formbody, Config.url_Visitmsu);
                Log.e("ressult", ressult);
                Type listType = new TypeToken<List<Msu.listSubbuilding>>() {
                }.getType();
                List<Msu.listSubbuilding> posts = gson.fromJson(ressult, listType);

                return posts;
            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Msu.listSubbuilding> ob) {
            super.onPostExecute(ob);

            if (progressBarStyleLarge.getVisibility() == View.VISIBLE) {
                progressBarStyleLarge.setVisibility(View.GONE);
            }


            if (ob != null) {
                if (ob.get(0).getId_sub_building() == 0) {
                    textvew_nodata.setVisibility(View.VISIBLE);
                    btn_viewlistdata.setVisibility(View.GONE);
                } else {
                    textvew_nodata.setVisibility(View.GONE);
                    btn_viewlistdata.setVisibility(View.VISIBLE);
                    listSubbuildings = ob;
                    btn_viewlistdata.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogListSubbuilding();
                        }
                    });

                }
            } else {
                btn_viewlistdata.setVisibility(View.GONE);
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }
        }
    }


    public class CreateViewDetail extends BaseAdapter {

        private List<Msu.listSubbuilding> list;
        private LayoutInflater inflater;
        private Context context;

        public CreateViewDetail(Context context, List<Msu.listSubbuilding> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view == null) {
                view = inflater.inflate(R.layout.content_item_detail, null);
            }

            TextView textview_sub_name = (TextView) view.findViewById(R.id.textview_sub_main);
            TextView textview_tel = (TextView) view.findViewById(R.id.tetxtview_tel);
            TextView tetxtview_email = (TextView) view.findViewById(R.id.textview_email);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_detail);

            textview_sub_name.setText("  " + list.get(i).getName_sub_building());
            textview_tel.setText("  " + list.get(i).getTel_sub_building());
            tetxtview_email.setText("  " + list.get(i).getEmail_sub_building());

            Glide.with(context)
                    .load(list.get(i).getImage_main_sub())
                    .placeholder(R.drawable.ic_wallpaper_black_24dp)
                    .error(R.drawable.ic_wallpaper_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            return view;
        }
    }

}
