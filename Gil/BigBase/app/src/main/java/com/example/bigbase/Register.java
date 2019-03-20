package com.example.bigbase;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Register extends AppCompatActivity implements OnClickListener{


    Button bacbtn;
    //로그인 버튼

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        bacbtn = (Button) findViewById(R.id.regdonebtn);


        bacbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(Register.this, Login.class);
                Register.this.startActivity(mainintent);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}

