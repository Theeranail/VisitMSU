package com.example.visitmsu.visitmsu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitmsu.visitmsu.model.CheckAccessInternet;
import com.example.visitmsu.visitmsu.model.CreateFuction;
import com.example.visitmsu.visitmsu.model.LoginMember;
import com.example.visitmsu.visitmsu.model.SharedPreferencesCheck;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser, editTextPass;
    String textUsername = "";
    String textPassword = "";
    private SharedPreferencesCheck sp;
    private CheckAccessInternet cAn;
    public RelativeLayout progrssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = new SharedPreferencesCheck(getApplicationContext());
        sp.checksharedPreLogin();

        final Button btn_login = (Button) findViewById(R.id.bun_login);
        editTextUser = (EditText) findViewById(R.id.editText_username);
        editTextPass = (EditText) findViewById(R.id.editText_password);
        progrssbar = (RelativeLayout) findViewById(R.id.contaiberprogressbar);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                textUsername = editTextUser.getText().toString();
                textPassword = editTextPass.getText().toString();

                if (textUsername.equals("") && textPassword.equals("")) {
                    CreateFuction createFuction = new CreateFuction(LoginActivity.this);
                    createFuction.AlertDialogOK("กรอกข้อมูลให้ครบ");
                } else {
                    cAn = new CheckAccessInternet(LoginActivity.this);
                    if (cAn.isConnectNet()) {
                        btn_login.setEnabled(false);
                        LoginMember loginMember = new LoginMember(LoginActivity.this, editTextUser.getText().toString(), editTextPass.getText().toString(), progrssbar);
                        loginMember.execute();
                        if (!btn_login.isEnabled()) {
                            btn_login.setEnabled(true);
                        }
                    } else {
                        btn_login.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        TextView textRegister = (TextView) findViewById(R.id.infoTxtCredits);
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
