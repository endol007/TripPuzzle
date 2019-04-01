package com.example.bigbase;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class Login extends AppCompatActivity implements OnClickListener{

    EditText id;
    EditText pw;
    Button loginbutton;
    Button registerbtn;
    Button findidbtn;
    Button snsloginbtn;
    //로그인 버튼

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        id = (EditText) findViewById(R.id.travelid);
        pw = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.loginbtn);
        registerbtn = (Button) findViewById(R.id.registerbtn);
        findidbtn = (Button) findViewById(R.id.findidbtn) ;
        snsloginbtn = (Button) findViewById(R.id.snsloginbtn) ;


        loginbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainintent = new Intent(Login.this, Main.class);
                Login.this.startActivity(mainintent);
                finish();

            }
        });

        registerbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent subintent = new Intent(Login.this, Register.class);
                Login.this.startActivity(subintent);
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


        snsloginbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (Login.this, SnsLogin.class);
                Login.this.startActivity(intent);
                finish();


            }



        });


    }

    @Override
    public void onClick(View v) {

    }

}
