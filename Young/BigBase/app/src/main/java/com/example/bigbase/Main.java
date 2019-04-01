package com.example.bigbase;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main extends AppCompatActivity implements OnClickListener{


    Button backbtn;
    //로그인 버튼

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        backbtn = (Button) findViewById(R.id.backbtn);


        backbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(Main.this, Login.class);
                Main.this.startActivity(mainintent);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}



