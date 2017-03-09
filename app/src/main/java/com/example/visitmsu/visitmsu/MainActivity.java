package com.example.visitmsu.visitmsu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.visitmsu.visitmsu.model.CheckAccessInternet;
import com.example.visitmsu.visitmsu.model.Config;
import com.example.visitmsu.visitmsu.model.CreateFuction;
import com.example.visitmsu.visitmsu.model.CustomViewmap;
import com.example.visitmsu.visitmsu.model.JsonHttp;
import com.example.visitmsu.visitmsu.model.ModifyImages;
import com.example.visitmsu.visitmsu.model.Msu;
import com.example.visitmsu.visitmsu.model.MyLocation;
import com.example.visitmsu.visitmsu.model.RecyclerItemClickListener;
import com.example.visitmsu.visitmsu.model.SearchBuilding;
import com.example.visitmsu.visitmsu.model.SharedPreferencesCheck;
import com.example.visitmsu.visitmsu.model.getlisternerlocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, getlisternerlocation {
    public String serverkey = "AIzaSyCl_NmVJJFLCOdDOudlM9tjPXCQ4yLaXPM";
    public GoogleMap gMap;
    public SharedPreferencesCheck spc;
    public MyLocation myLocation;
    private CheckAccessInternet cAn;
    public ProgressBar progressBar;
    public List<Msu.ListData> listarraymsu;
    public List<Msu.getSearchList> listMsusearch;
    public ArrayList<Marker> markerArrayList;
    public ListView list_item_search;
    public RecyclerView rv_list_detail;
    public SharedPreferencesCheck sp;
    public String inputsesrch = "";
    public double Lattitude = 0;
    public double Logtitude = 0;
    public boolean status_closeListSelectSearch = true;
    public LatLng latLngStart;
    public Polyline line;
    public PolylineOptions polygonOptions;
    public Marker myMarker;
    public RelativeLayout content_navigeater;
    public TextView textvew_shownavigater;
    public FloatingActionButton btn_iconrefresh, btn_iconremylocation, btn_iconNavigate, btn_openClosepopup, btn_threeD;
    Marker marker;
    public boolean statusbtn_Navigate = true;
    public boolean FocusSatrt = false;
    //39.904069, 116.406742

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        content_navigeater = (RelativeLayout) findViewById(R.id.content_navigeater);
        textvew_shownavigater = (TextView) findViewById(R.id.textvew_shownavigater);
        btn_iconrefresh = (FloatingActionButton) findViewById(R.id.btn_iconrefresh);
        btn_iconremylocation = (FloatingActionButton) findViewById(R.id.btn_iconremylocation);
        btn_iconNavigate = (FloatingActionButton) findViewById(R.id.btn_iconNavigate);
        btn_openClosepopup = (FloatingActionButton) findViewById(R.id.btn_openClosepopup);
        btn_threeD = (FloatingActionButton) findViewById(R.id.btn_threeD);
        spc = new SharedPreferencesCheck(MainActivity.this);
        spc.checksharedPre();

        myLocation = new MyLocation(MainActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.viewmap);
        mapFragment.getMapAsync(this);

        gMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewmap)).getMap();

        cAn = new CheckAccessInternet(MainActivity.this);
        if (cAn.isConnectNet()) {
            new LoadDataMSU(MainActivity.this).execute();
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
        }

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            Boolean search_ = bundle.getBoolean("search_");
            if (search_) {
                DialogSerch();
            }
        }

        btn_iconrefresh.setOnClickListener(this);
        btn_iconremylocation.setOnClickListener(this);
        btn_iconNavigate.setOnClickListener(this);
        btn_openClosepopup.setOnClickListener(this);
        btn_threeD.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        spc.checksharedPre();

        myLocation.StartLocation();
        gMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewmap)).getMap();

    }

    @Override
    protected void onStop() {
        super.onStop();

        myLocation.Stoplocation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        spc.checksharedPre();
        myLocation.StartLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocation.Stoplocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if ((progressl != null) && progressl.isShowing()) {
//            progressl.dismiss();
//            progressl = null;
//        }
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
            DialogSerch();
            //Intent intent = new Intent(MainActivity.this, SearchAcivity.class);
            // startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     *  รับค่า googleMap พร้อมทำงาน
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //    gMap = googleMap;

        final LatLng coordinateDefult = new LatLng(16.2466625, 103.2504303);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinateDefult, 16));
    }

    /*
     *  รับค่า Location
     */
    @Override
    public void getLatliogLocation(Location location) {
        Lattitude = location.getLatitude();
        Logtitude = location.getLongitude();
        myLocation.setLatltitude(location.getLatitude());
        myLocation.setLogtitude(location.getLongitude());
        Log.e("latlog", String.valueOf(Lattitude));
        if (Lattitude != 0) {
            if (FocusSatrt == false) {
                setFocusMyLocation();
            }
            setSnippetTitle();


        }

    }

    /*
     *  ปักหมุดตำแหน่งปัจุจบัน
     */
    public void setFocusMyLocation() {
        if (myMarker != null)
            myMarker.remove();
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lattitude, Logtitude), 16));
        myMarker = gMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lattitude, Logtitude))
                .title("Mylocation")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mypoint)));
    }

    /*
     *  ปักหมุดข้อมูล
     */
    public void setMarkerpoint(final List<Msu.ListData> listarraymsu) {

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ModifyImages modifyImages = new ModifyImages();
                markerArrayList = new ArrayList<Marker>();
                removeMarkers();
                View vMarker = null;
                for (int i = 0; i < listarraymsu.size(); i++) {
                    if (listarraymsu.get(i).getId_type_building() == 1) {
                        vMarker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                    } else {
                        vMarker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker2, null);
                    }
                    marker = null;
                    marker = gMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.valueOf(listarraymsu.get(i).getLatitude_building()).doubleValue(),
                                    Double.valueOf(listarraymsu.get(i).getLongitude_building()).doubleValue()))
                            .icon(BitmapDescriptorFactory.fromBitmap(modifyImages.createDrawableFromView(MainActivity.this, vMarker)))
                            .title(listarraymsu.get(i).getName_building() + ", M" + i)
                            .snippet("กำลังคำนวนระยะทาง...")
                            .anchor(0.5f, 1));
                    markerArrayList.add(marker);
                    markerArrayList.get(i).hideInfoWindow();
                    gMap.setInfoWindowAdapter(new CustomViewmap.setViewTitle(MainActivity.this));
                    gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(final Marker marker) {
                            if (marker.getTitle().equals("Mylocation")) {
                                return;
                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setTitle("");
                                alertDialogBuilder.setMessage("กรุณาลือก");
                                alertDialogBuilder.setPositiveButton("ดูรายละเอียด",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                //Toast.makeText(MainActivity.this,arg1+"::"+marker.getId()+"::"+CreateFuction.splitsnippet(marker.getTitle()),Toast.LENGTH_SHORT).show();

                                                ClickShowdetail(CreateFuction.splitsnippet(marker.getTitle()));
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("ขอเส้นทาง", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        setFocusMarker(marker.getPosition().latitude, marker.getPosition().longitude);
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }
                    });
                }
            }
        });

    }

    public void setSnippetTitle() {
        if (markerArrayList != null) {
            for (final Marker marker : markerArrayList) {

                GoogleDirection.withServerKey("AIzaSyCl_NmVJJFLCOdDOudlM9tjPXCQ4yLaXPM").from(new LatLng(Lattitude, Logtitude))
                        .to(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                        .transportMode(TransportMode.DRIVING).language(Language.THAI).unit(Unit.METRIC).alternativeRoute(false)
                        .execute(new DirectionCallback() {

                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                String status = direction.getStatus();
                                if (status.equals(RequestResult.OK)) {
                                    Route route = direction.getRouteList().get(0);
                                    Leg leg = route.getLegList().get(0);

                                    Info distanceInfo = leg.getDistance();
                                    Info durationInfo = leg.getDuration();
                                    String distance = distanceInfo.getText();
                                    String Distance = "";
                                    Double dist = (myLocation.CalculationDistance(Lattitude, Logtitude, marker.getPosition().latitude, marker.getPosition().longitude) * 1000);
                                    NumberFormat formatter = new DecimalFormat("#0.00");
                                    marker.setSnippet("ระยะทาง :" + distance + "\n" + "อยู่ห่างออกไป: " + formatter.format(dist) + "เมตร.");
                                } else {
                                    Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                                    marker.setSnippet("Nodata");
                                }

                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {
                            }
                        });

            }
        }
    }

    /*
     *  FocusMarker
     */
    public void setFocusMarker(Double lattitude, Double logtitude) {
        final LatLng coordinateDefult = new LatLng(lattitude, logtitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinateDefult, 16));
        for (int i = 0; i < markerArrayList.size(); i++) {
            if (markerArrayList.get(i).getPosition().equals(coordinateDefult)) {
                markerArrayList.get(i).hideInfoWindow();
                markerArrayList.get(i).showInfoWindow();
                CreateDirectionMap(coordinateDefult);
                return;
            }
        }
    }


    /*
     *  เครีย marker
     */
    private void removeMarkers() {
        for (Marker marker : markerArrayList) {
            marker.remove();
        }
        markerArrayList.clear();
    }

    /*
     *  ดูรายละเอียด
     */
    public void ClickShowdetail(int position) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("Name_building", listarraymsu.get(position).getName_building());
        intent.putExtra("id_building", listarraymsu.get(position).getId_building());
        intent.putExtra("Tel_building", listarraymsu.get(position).getTel_building());
        intent.putExtra("email_building", listarraymsu.get(position).getEmail_building());

        intent.putExtra("Image_main", listarraymsu.get(position).getImage_main());
        intent.putExtra("latitude_building", listarraymsu.get(position).getLatitude_building());
        intent.putExtra("longitude_building", listarraymsu.get(position).getLongitude_building());
        intent.putExtra("detail_building", listarraymsu.get(position).getDetail_building());
        intent.putExtra("address_building", listarraymsu.get(position).getAddress_building());
        intent.putExtra("id_type_building", listarraymsu.get(position).getId_type_building());

        startActivity(intent);

    }

    /*
     *  Dialog ค้นหา
     */
    public void DialogSerch() {

        final AlertDialog.Builder dai_builder = new AlertDialog.Builder(MainActivity.this);
        final SearchBuilding searchBuilding = new SearchBuilding();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.content_serach, null);
        list_item_search = (ListView) view.findViewById(R.id.list_item_search);
        rv_list_detail = (RecyclerView) view.findViewById(R.id.rv_list_detail);
        rv_list_detail.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        final TextView textShowsubserch = (TextView) view.findViewById(R.id.textShowsubserch);
        final EditText inputSearch = (EditText) view.findViewById(R.id.inputSearch);

        textShowsubserch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status_closeListSelectSearch) {
                    status_closeListSelectSearch = false;
                    if (list_item_search.getVisibility() == View.VISIBLE) {
                        list_item_search.setVisibility(View.GONE);
                        textShowsubserch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
                    }
                } else {
                    status_closeListSelectSearch = true;
                    if (list_item_search.getVisibility() == View.GONE) {
                        list_item_search.setVisibility(View.VISIBLE);
                        textShowsubserch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0);
                    }
                }
            }
        });

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputsesrch = inputSearch.getText().toString();
                    new GetSeachMSu(MainActivity.this, "", inputsesrch, Lattitude, Logtitude).execute();
                }
                return false;
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, searchBuilding.AddrayyObj(), R.layout.listmenu_search
                , new String[]{"name", "img"}, new int[]{R.id.namemenu, R.id.imgicon});
        list_item_search.setAdapter(simpleAdapter);

        list_item_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = searchBuilding.AddrayyObj().get(position).get("name").toString();
                Log.d("nameS", name);
                GetSeachMSu GetSeachMSu = (GetSeachMSu) new GetSeachMSu(MainActivity.this, name, inputsesrch, Lattitude, Logtitude).execute();
                if (list_item_search.getVisibility() == View.VISIBLE) {
                    status_closeListSelectSearch = false;
                    list_item_search.setVisibility(View.GONE);
                    textShowsubserch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
                }

            }
        });

        dai_builder.setTitle("ค้นหา อาคาร/ผู้ประกอบการ");
        dai_builder.setView(view);

        dai_builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog ad = dai_builder.show();
        rv_list_detail.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, rv_list_detail, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        ad.dismiss();
                        setFocusMarker(Double.valueOf(listMsusearch.get(position).getLatitude_building()).doubleValue(), Double.valueOf(listMsusearch.get(position).getLongitude_building()).doubleValue());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    /*
     *  เเสดงเส้นทาง
     */
    public void CreateDirectionMap(LatLng latLngStop) {

        Double lat = null;
        Double longt = null;
        lat = Lattitude;
        longt = Logtitude;

        if (lat == null)
            lat = 13.7335222;
        if (longt == null)
            longt = 100.5375236;

        latLngStart = new LatLng(lat, longt);

        if (latLngStart != null) {
            GoogleDirection.withServerKey(serverkey)
                    .from(latLngStart)
                    .to(latLngStop)
                    .transportMode(TransportMode.DRIVING)
                    .transitMode(TransitMode.BUS)
                    .language(Language.THAI)
                    .unit(Unit.METRIC)
                    .alternativeRoute(false)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            String status = direction.getStatus();
                            if (status.equals(RequestResult.OK)) {

                                if (content_navigeater.getVisibility() == View.GONE) {
                                    content_navigeater.setVisibility(View.VISIBLE);
                                }
                                if (btn_openClosepopup.getVisibility() == View.GONE) {
                                    btn_openClosepopup.setVisibility(View.VISIBLE);
                                }

                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);

                                Info distanceInfo = leg.getDistance();
                                Info durationInfo = leg.getDuration();

                                String distance = distanceInfo.getText();
                                String duration = durationInfo.getText();

                                String html = "";
                                for (int i = 0; i < leg.getStepList().size(); i++) {
                                    html += leg.getStepList().get(i).getHtmlInstruction() + "\n";
                                    Log.e("getStepList", leg.getStepList().get(i).getHtmlInstruction());
                                }
                                // TextHTMLNavigeater = String.valueOf(Html.fromHtml(html));

                                textvew_shownavigater.setText(Html.fromHtml(html));
                                List<Route> ro = direction.getRouteList();
                                for (Route r : ro) {
                                    Log.e("rr", String.valueOf(r.getLegList().get(0).getStartLocation().getCoordination()));
                                }
                                if (line != null)
                                    line.remove();
//                                List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
//                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(MainActivity.this, stepList, 5, Color.RED, 3, Color.BLUE);
//                                for (PolylineOptions polylineOption : polylineOptionList) {
//                                    line = gMap.addPolyline(polylineOption);
//                                }

                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();

                                polygonOptions = DirectionConverter
                                        .createPolyline(MainActivity.this, directionPositionList, 5, Color.RED);
                                line = gMap.addPolyline(polygonOptions);

                            } else if (status.equals(RequestResult.REQUEST_DENIED)) {
                                Log.e("REQUEST_DENIED", RequestResult.REQUEST_DENIED);
                            } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {
                                Log.e("REQUEST_DENIED", RequestResult.REQUEST_DENIED);
                            } else if (status.equals(RequestResult.NOT_FOUND)) {
                                Log.e("NOT_FOUND", RequestResult.NOT_FOUND);
                            } else if (status.equals(RequestResult.ZERO_RESULTS)) {
                                Log.e("ZERO_RESULTS ", RequestResult.ZERO_RESULTS);
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            Log.e("onDirectionFailure ", t.toString());
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_iconrefresh:
                if (content_navigeater.getVisibility() == View.VISIBLE) {
                    content_navigeater.setVisibility(View.GONE);
                }
                if (btn_openClosepopup.getVisibility() == View.VISIBLE) {
                    btn_openClosepopup.setVisibility(View.GONE);
                }
                if (line != null)
                    line.remove();
                if (cAn.isConnectNet()) {
                    new LoadDataMSU(MainActivity.this).execute();

                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_iconremylocation:
                setFocusMyLocation();
                break;
            case R.id.btn_iconNavigate:
                if (statusbtn_Navigate == true) {
                    FocusSatrt = true;
                    statusbtn_Navigate = false;
                    btn_iconNavigate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#757575")));
                    Toast.makeText(MainActivity.this, "Close Navigate", Toast.LENGTH_LONG).show();
                } else {
                    statusbtn_Navigate = true;
                    FocusSatrt = false;
                    btn_iconNavigate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2196F3")));
                    Toast.makeText(MainActivity.this, "Open Navigate", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.btn_openClosepopup:
                if (content_navigeater.getVisibility() == View.VISIBLE) {
                    content_navigeater.setVisibility(View.GONE);
                } else {
                    content_navigeater.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_threeD:
                Toast.makeText(MainActivity.this, "เขตพื้นที่ของคุณไม่สนับสนุนรูปแบบ 3D", Toast.LENGTH_LONG).show();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.904069, 116.406742), 17));

                break;
        }
    }

    /*
     *  โหลดข้อมูล
     */
    public class LoadDataMSU extends AsyncTask<Object, Integer, List<Msu.ListData>> {

        private Context context;

        public LoadDataMSU(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            if (progressBar.getVisibility() == View.GONE) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<Msu.ListData> doInBackground(Object... objects) {
            try {

                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();

                RequestBody formBody = new FormBody.Builder()
                        .add("function", "getLisData_msu")
                        .build();
                String ressult = jsonHttp.Postdata(formBody, Config.url_Visitmsu);
                Log.e("ressult", ressult);
                Type listType = new TypeToken<List<Msu.ListData>>() {
                }.getType();
                List<Msu.ListData> data = gson.fromJson(ressult, listType);
                if (data != null) {
                    setMarkerpoint(data);
                }

                return data;

            } catch (RuntimeException e) {
                String msg = (e.getMessage() == null) ? "Load failed!" : e.getMessage();
                Log.e("RuntimeException", msg);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Msu.ListData> listDatas) {
            super.onPostExecute(listDatas);

            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
            if (listDatas != null) {
                if (listDatas.get(0).getId_building() == 0) {
                    Toast.makeText(context, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
                } else {
                    listarraymsu = listDatas;
                }

            } else {

                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }

        }

    }

    /*
     *  ค้นหา
     */
    public class GetSeachMSu extends AsyncTask<Object, Integer, List<Msu.getSearchList>> {
        public Context context;
        public ProgressDialog progress;
        public String naSearch;
        public String inputSearch;
        public Double lattitude;
        public Double logtitude;


        public GetSeachMSu(Context context, String naSearch, String inputSearch, Double lattitude, Double logtitude) {
            this.context = context;
            this.naSearch = naSearch;
            this.inputSearch = inputSearch;
            this.lattitude = lattitude;
            this.logtitude = logtitude;

        }

        @Override
        protected void onPreExecute() {

            progress = new ProgressDialog(context);
            progress.setMessage("กำลังค้นหา...");
            progress.setCancelable(false);
            progress.show();

        }

        @Override
        protected List<Msu.getSearchList> doInBackground(Object... objects) {
            try {

                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();
                RequestBody formBody = new FormBody.Builder()
                        .add("function", "SearchMSU")
                        .add("inputeasrch", inputSearch)
                        .add("lat", String.valueOf(lattitude))
                        .add("lng", String.valueOf(logtitude))
                        .add("keyword", naSearch)
                        .build();
                String ressult = jsonHttp.Postdata(formBody, Config.url_Visitmsu);
                Log.e("ressultGetSeachfood", ressult);
                Type listType = new TypeToken<List<Msu.getSearchList>>() {
                }.getType();
                List<Msu.getSearchList> posts = gson.fromJson(ressult, listType);

                return posts;


            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Msu.getSearchList> ob) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
            rv_list_detail.removeAllViewsInLayout();

            if (ob != null) {
                if (ob.get(0).getId_building() == 0) {
                    Toast.makeText(context, "ไม่พบข้อมูล!", Toast.LENGTH_LONG).show();
                } else {
                    listMsusearch = ob;
                    rv_list_detail.setAdapter(new SearchBuilding().new RecycleViewAdapter(context, ob, Lattitude, Logtitude));
                }
            } else {
                Toast.makeText(context, "ไม่พบข้อมูล", Toast.LENGTH_LONG).show();
            }

        }

    }

}

