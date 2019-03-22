package com.example.bigbase;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Login extends AppCompatActivity implements OnClickListener{

    EditText id;
    EditText pw;

    Button findidbtn;
    //로그인 버튼

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        id = (EditText) findViewById(R.id.travelid);
        pw = (EditText) findViewById(R.id.password);
        final Button loginbutton = (Button) findViewById(R.id.loginbtn);
        final Button registerbtn = (Button) findViewById(R.id.registerbtn);
        findidbtn = (Button) findViewById(R.id.findidbtn) ;


        final EditText regid = (EditText) findViewById(R.id.regid);
        final EditText regpw = (EditText) findViewById(R.id.regpw);

        loginbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = regid.getText().toString();
                final String userPassword = regpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userID = jsonResponse.getString("userID");
                                String userPassword = jsonResponse.getString("userPassword");
                                Intent intent = new Intent(Login.this, Main.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });

        registerbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerintent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerintent);
                finish();

            }
        });


        findidbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent minintent = new Intent(Login.this, Findid.class);
                Login.this.startActivity(minintent);
                finish();

            }
        });


    }

    @Override
    public void onClick(View v) {

    }

}
