package com.example.visitmsu.visitmsu.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.visitmsu.visitmsu.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginMember extends AsyncTask<Object, Integer, List<RegisterMember.ListMember>> {

    Context context;
    List<RegisterMember.ListMember> users;
    ProgressDialog progress;
    private String Username;
    private String Password;
    SharedPreferences sp;
    public RelativeLayout  progressbar;



    public LoginMember(Context context, String Username, String Passwors, RelativeLayout progressbar) {
        this.context = context;
        this.Username = Username;
        this.Password = Passwors;
        this.progressbar = progressbar;
    }

    @Override
    protected void onPreExecute() {
        if(progressbar.getVisibility() == View.GONE) {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected List<RegisterMember.ListMember> doInBackground(Object... params) {

        try {

            Gson gson = new Gson();

            JsonHttp jsonHttp = new JsonHttp();

            RequestBody formBody = new FormBody.Builder()
                    .add("function", "Login")
                    .add("Username", Username)
                    .add("Password", Password)
                    .build();
            String ressult = jsonHttp.Postdata(formBody,Config.url_login);
            Log.e("ressult", ressult);
            Type listType = new TypeToken<List<RegisterMember.ListMember>>() {
            }.getType();
            List<RegisterMember.ListMember> posts = gson.fromJson(ressult, listType);

            return posts;

        } catch (RuntimeException e) {
            Log.e("RuntimeException", e.getMessage());

            return null;
        }
    }

    @Override
    protected void onPostExecute(List<RegisterMember.ListMember> ob) {
        super.onPostExecute(ob);

        if (progressbar.getVisibility() == View.VISIBLE) {
            progressbar.setVisibility(View.GONE);
        }

        if (ob != null) {
            users = ob;

            if (users.get(0).getRs() == "") {
                Toast.makeText(context, "รหัสผ่านหรือชื่อผู้ใช้ไม่ถูกต้อง", Toast.LENGTH_LONG).show();
            } else {
                Log.e("username", users.get(0).getUsername());
                Log.e("Name", users.get(0).getName_member());
                Log.e("Id", users.get(0).getId_member());

                sp = context.getSharedPreferences(SharedPreferencesCheck.Shared_PreferencesName, Context.MODE_APPEND);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("usernamme", users.get(0).getName_member());
                editor.putString("user_id", users.get(0).getId_member());
                editor.commit();


                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }

        } else {
            Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
        }

    }


}
