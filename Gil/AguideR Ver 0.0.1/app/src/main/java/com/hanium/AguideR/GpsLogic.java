package com.hanium.AguideR;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GpsLogic extends AppCompatActivity {


    private GpsInfo gps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpslogic);


        TextView getgpsresult = (TextView) findViewById(R.id.getgpsresult);

        Button gpslogictologinbtn = (Button) findViewById(R.id.gpslogictologinbtn);



        gpslogictologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logingointent = new Intent(GpsLogic.this, Login.class);
                GpsLogic.this.startActivity(logingointent);
                finish();
            }
        });



        gps = new GpsInfo(GpsLogic.this);
        final double latitude = gps.getLatitude();

        getgpsresult.setText(String.valueOf(latitude));

        //다른 클래스 에서 상속 받은애 다시 선언해 주고 메소드 뽑아 오는 모습








    }
}
