package com.hanium.AguideR;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    String[] permission_list = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    //퍼미션 배열 선언 부분




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        //퍼미션 부분



    }

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }

            if(chk == PackageManager.PERMISSION_GRANTED){
                //권한 허용을여부를 확인하는 창을 띄운다
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainintent = new Intent(MainActivity.this, Login.class);
                        MainActivity.this.startActivity(mainintent);
                        finish();
                    }
                }, 2000);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int i = 0; i < grantResults.length; i++) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    Intent mainintent = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(mainintent);
                    finish();


                    //허용 됬다면 ㄱㄱ

                } else {
                    Toast.makeText(getApplicationContext(), "권한설정해주세요", Toast.LENGTH_LONG).show();
                    finish();

                    //허용 안됬으면 허용 해라
                }
            }

        }
    }
}


