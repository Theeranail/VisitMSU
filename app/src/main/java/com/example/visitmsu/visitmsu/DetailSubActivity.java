package com.example.visitmsu.visitmsu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.visitmsu.visitmsu.model.CreateFuction;
import com.example.visitmsu.visitmsu.model.SharedPreferencesCheck;

import static android.R.attr.button;

public class DetailSubActivity extends AppCompatActivity implements View.OnClickListener {

    public String Id_sub_building,
            id_building,
            name_sub_duilding,
            Tel_sub_building,
            email_sub_building,
            Image_main,
            detail_building,
            address_building;
    public TextView text_name_main, text_email, text_tel, text_address, textvew_nodata, textview_detail;
    public ImageView img_main_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        Id_sub_building = bundle.getString("Id_sub_building");
        id_building = bundle.getString("id_building");
        name_sub_duilding = bundle.getString("name_sub_duilding");
        Tel_sub_building = bundle.getString("Tel_sub_building");
        email_sub_building = bundle.getString("email_building");
        Image_main = bundle.getString("Image_main");
        detail_building = bundle.getString("detail_building");
        address_building = bundle.getString("address_building");


        text_name_main = (TextView) findViewById(R.id.text_name_main);
        text_email = (TextView) findViewById(R.id.text_email);
        text_tel = (TextView) findViewById(R.id.text_tel);
        text_address = (TextView) findViewById(R.id.text_address);
        img_main_sub = (ImageView) findViewById(R.id.img_main);
        textvew_nodata = (TextView) findViewById(R.id.textvew_nodata);
        textview_detail = (TextView) findViewById(R.id.text_detail);

        getSupportActionBar().setTitle("รายละเอียด " + name_sub_duilding);

        //   Log.e("Image_mains",Image_mains);
        Glide.with(DetailSubActivity.this)
                .load(Image_main)
                .placeholder(R.drawable.ic_wallpaper_black_24dp)
                .error(R.drawable.ic_wallpaper_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img_main_sub);

        text_name_main.setText(" " + name_sub_duilding);
        if (email_sub_building != null && !email_sub_building.isEmpty() && !email_sub_building.equals("null")) {
            text_email.setText(" " + email_sub_building);
        } else {
            text_email.setText(" " + "ไม่มีข้อมูล");
        }
        if (Tel_sub_building != null && !Tel_sub_building.isEmpty() && !Tel_sub_building.equals("null")) {
            text_tel.setText(" " + Tel_sub_building);
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
            Intent intent = new Intent(DetailSubActivity.this, MainActivity.class);
            intent.putExtra("search_", true);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_tel:
                if (Tel_sub_building != null && !Tel_sub_building.isEmpty() && !Tel_sub_building.equals("null")) {
                    CreateFuction createFuction = new CreateFuction(DetailSubActivity.this);
                    createFuction.DialogAlertPhone(Tel_sub_building);
                }
                break;
            case R.id.text_email:
                if (email_sub_building != null && !email_sub_building.isEmpty() && !email_sub_building.equals("null")) {
                    CreateFuction createFuction = new CreateFuction(DetailSubActivity.this);
                    createFuction.DialogAlertSendmail(email_sub_building, name_sub_duilding);
                }
                break;
        }
    }


}
