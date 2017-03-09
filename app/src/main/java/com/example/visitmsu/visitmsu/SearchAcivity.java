package com.example.visitmsu.visitmsu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitmsu.visitmsu.model.Config;
import com.example.visitmsu.visitmsu.model.JsonHttp;
import com.example.visitmsu.visitmsu.model.Msu;
import com.example.visitmsu.visitmsu.model.MyLocation;
import com.example.visitmsu.visitmsu.model.SearchBuilding;
import com.example.visitmsu.visitmsu.model.SharedPreferencesCheck;
import com.example.visitmsu.visitmsu.model.getlisternerlocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SearchAcivity extends AppCompatActivity {

}
