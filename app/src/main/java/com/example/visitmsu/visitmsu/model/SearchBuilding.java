package com.example.visitmsu.visitmsu.model;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.visitmsu.visitmsu.MainActivity;
import com.example.visitmsu.visitmsu.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchBuilding {
    public static final String[] lismenu = {"อาคารมหาวิทยาลัย", "ผู้ประกอบการ", "ตู้ ATM", "โรงอาหาร"};
    public static final int[] iconlistmenu = {R.drawable.ic_business_black_24dp, R.drawable.ic_store_mall_directory_black_24dp, R.drawable.ic_local_atm_black_24dp, R.drawable.ic_local_dining_black_24dp};
    public double distace = 0;

    public String dutation;
    public String time;
    public String navigester;

    public static String Simple = "";


    public SearchBuilding() {
    }

    public ArrayList<HashMap<String, Object>> AddrayyObj() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;

        for (int i = 0; i < lismenu.length; i++) {
            map = new HashMap<String, Object>();
            map.put("name", lismenu[i]);
            map.put("img", iconlistmenu[i]);
            list.add(map);

        }
        return list;
    }

    public class RecycleViewAdapter extends RecyclerView.Adapter<ViewHolder>  {
        private List<Msu.getSearchList> list;
        private LayoutInflater inflater;
        private Context context;
        private Double lat = 0.0;
        private Double log = 0.0;
        private MyLocation myLocation;

        public RecycleViewAdapter(Context context, List<Msu.getSearchList> objects, Double lat, Double log) {
            this.context = context;
            this.list = objects;
            this.lat = lat;
            this.log = log;

            myLocation = new MyLocation(context);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list_serch, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.TextnameFood.setText((list.get(position).getName_building()));
            Glide.with(context)
                    .load(list.get(position).getImage_main())
                    .placeholder(R.drawable.ic_wallpaper_black_24dp)
                    .error(R.drawable.ic_wallpaper_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_show);

            if (list.get(position).getName_sub_building() != null && !list.get(position).getName_sub_building().isEmpty() && !list.get(position).getName_sub_building().equals("null")) {
                String Name_sub_building = new String(list.get(position).getName_sub_building());
                if (Name_sub_building.length() > 59)
                    Name_sub_building = Name_sub_building.substring(0, 58) + "...";
                holder.textAddress.setText("ที่อยู่: " + Name_sub_building);
            } else {
                holder.textAddress.setText("");
            }

            distace = myLocation.CalculationDistance(lat, log,
                    Double.valueOf(list.get(position).getLatitude_building()).doubleValue(),
                    Double.valueOf(list.get(position).getLongitude_building()).doubleValue());

            LatLng latLng = new LatLng(Double.valueOf(list.get(position).getLatitude_building()).doubleValue(),
                    Double.valueOf(list.get(position).getLongitude_building()).doubleValue());

            setDrawing(holder, lat, log, latLng);
            setWALKING(holder, lat, log, latLng);
            setBICYCLING(holder, lat, log, latLng);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text_distane,
                textview_time_walk,
                textview_time_bike,
                textview_time_drawing,
                TextnameFood,
                textAddress;
        public ImageView img_show;

        public ViewHolder(View itemView) {
            super(itemView);

            TextnameFood = (TextView) itemView.findViewById(R.id.name_building);
            textAddress = (TextView) itemView.findViewById(R.id.text_sub_name);
            text_distane = (TextView) itemView.findViewById(R.id.content_search_distane);
            textview_time_walk = (TextView) itemView.findViewById(R.id.textview_time_walk);
            textview_time_bike = (TextView) itemView.findViewById(R.id.textview_time_bike);
            textview_time_drawing = (TextView) itemView.findViewById(R.id.textview_time_drawing);
            img_show = (ImageView) itemView.findViewById(R.id.img_building);

        }
    }

    //DRIVING
    public void setDrawing(final ViewHolder viewHolder, double lat, double log, LatLng latLng) {

        GoogleDirection.withServerKey("AIzaSyCl_NmVJJFLCOdDOudlM9tjPXCQ4yLaXPM")
                .from(new LatLng(lat, log))
                .to(latLng)
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

                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);

                            Info distanceInfo = leg.getDistance();
                            Info durationInfo = leg.getDuration();

                            String distance = distanceInfo.getText();
                            String duration = durationInfo.getText();

                            viewHolder.text_distane.setText(" " + distance);
                            viewHolder.textview_time_drawing.setText(" " + duration);

                        } else if (status.equals(RequestResult.REQUEST_DENIED)) {

                            viewHolder.text_distane.setText(" " + RequestResult.REQUEST_DENIED);
                            viewHolder.textview_time_drawing.setText(" " + RequestResult.REQUEST_DENIED);

                        } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {

                            viewHolder.text_distane.setText(" " + RequestResult.UNKNOWN_ERROR);
                            viewHolder.textview_time_drawing.setText(" " + RequestResult.UNKNOWN_ERROR);

                        } else if (status.equals(RequestResult.NOT_FOUND)) {

                            viewHolder.text_distane.setText(" " + RequestResult.NOT_FOUND);
                            viewHolder.textview_time_drawing.setText(" " + RequestResult.NOT_FOUND);

                        } else if (status.equals(RequestResult.ZERO_RESULTS)) {

                            viewHolder.text_distane.setText(" " + RequestResult.ZERO_RESULTS);
                            viewHolder.textview_time_drawing.setText(" " + RequestResult.ZERO_RESULTS);

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.e("onDirectionFailure ", t.toString());
                    }
                });
    }

    //WALKING
    public void setWALKING(final ViewHolder viewHolder, double lat, double log, LatLng latLng) {
        GoogleDirection.withServerKey("AIzaSyCl_NmVJJFLCOdDOudlM9tjPXCQ4yLaXPM")
                .from(new LatLng(lat, log))
                .to(latLng)
                .transportMode(TransportMode.WALKING)
                .transitMode(TransitMode.BUS)
                .language(Language.THAI)
                .unit(Unit.METRIC)
                .alternativeRoute(false)
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
                            String duration = durationInfo.getText();
                            viewHolder.textview_time_walk.setText(" " + duration);

                        } else if (status.equals(RequestResult.REQUEST_DENIED)) {

                        } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {

                        } else if (status.equals(RequestResult.NOT_FOUND)) {
                        } else if (status.equals(RequestResult.ZERO_RESULTS)) {
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.e("onDirectionFailure ", t.toString());
                    }
                });
    }

    //BICYCLING
    public void setBICYCLING(final ViewHolder viewHolder, double lat, double log, LatLng latLng) {
        GoogleDirection.withServerKey("AIzaSyCl_NmVJJFLCOdDOudlM9tjPXCQ4yLaXPM")
                .from(new LatLng(lat, log))
                .to(latLng)
                .transportMode(TransportMode.BICYCLING)
                .transitMode(TransitMode.BUS)
                .language(Language.THAI)
                .unit(Unit.METRIC)
                .alternativeRoute(false)
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
                            String duration = durationInfo.getText();
                            viewHolder.textview_time_bike.setText(" " + duration);

                        } else if (status.equals(RequestResult.REQUEST_DENIED)) {

                        } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {

                        } else if (status.equals(RequestResult.NOT_FOUND)) {
                        } else if (status.equals(RequestResult.ZERO_RESULTS)) {
                            viewHolder.textview_time_bike.setText("  " + RequestResult.ZERO_RESULTS + ": " + "สนับสนุในบางพื้นที่เท่านัน");
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.e("onDirectionFailure ", t.toString());
                    }
                });
    }
}
