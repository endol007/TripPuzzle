package com.example.bigbase;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class Findid extends AppCompatActivity implements OnClickListener{


    Button finddonebtn;
    Button findbtn;
    //로그인 페이지로 가는 버튼

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findid);


        finddonebtn = (Button) findViewById(R.id.finddonebtn);
        findbtn = (Button) findViewById(R.id.findbtn);

        finddonebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(Findid.this, Login.class);
                Findid.this.startActivity(mainintent);
                finish();
            }
        });

        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Findid.this, "가입 정보가 없는걸요?", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onClick(View v) {

    }
}
