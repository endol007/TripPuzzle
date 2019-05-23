package com.hanium.AguideR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JspRegister3 extends Activity {

    Button registerbtn, regtolbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jspregister3);
        //execute의 매개값은
        //sendMsg = "id="+strings[0]+"&pwd="+strings[1];
        //doInBackround에서 사용된 문자열 배열에 필요한 값을 넣습니다.
        // 그리고 jsp로 부터 리턴값을 받아야할 때는
        //String returns = task.execute("rain483","1234").get();
        //처럼 사용하시면 되는데요. get()에서 에러가 발생할 수 있어서 try catch문으로
        //감싸야에러가 나지 않습니다.


        final EditText regid = (EditText) findViewById(R.id.regid);
        final EditText regpw = (EditText) findViewById(R.id.regpw);
        final EditText regname = (EditText) findViewById(R.id.regname);
        final EditText regage = (EditText) findViewById(R.id.regage);
        final EditText regphone = (EditText) findViewById(R.id.regphone);
        final EditText regmail = (EditText) findViewById(R.id.regmail);
        final EditText regsex = (EditText) findViewById(R.id.regsex);

        registerbtn = (Button) findViewById(R.id.regbtn);
        regtolbtn = (Button) findViewById(R.id.regtolbtn);









        regtolbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tologinintent = new Intent(JspRegister3.this, Login.class);
                JspRegister3.this.startActivity(tologinintent);
                finish();

            }
        });





        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = regid.getText().toString();
                String userPassword = regpw.getText().toString();
                String userName = regname.getText().toString();
                int userAge = Integer.parseInt(regage.getText().toString());
                String userSex = regsex.getText().toString();
                String userPhone = regphone.getText().toString();
                String userMail = regmail.getText().toString();


                try {
                    String result;
                    CustomTask task = new CustomTask();
                    result = task.execute(userID, userPassword, userName, String.valueOf(userAge), userSex, userPhone, userMail).get();
                    Log.i("리턴 값", result);

                } catch (Exception e) {

                }

            }
        });




    }


    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @SuppressLint("WrongThread")
        @Override
        // doInBackground의 매개값이 문자열 배열인데요. 보낼 값이 여러개일 경우를 위해 배열로 합니다.
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://182.210.78.154:8080/Register.jsp");//보낼 jsp 주소를 ""안에 작성합니다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&userID="+strings[0]+"&userPassword="+strings[1]+"&userName="+strings[2]+"&userAge="+strings[3]+"&userSex="+strings[4]+"&userPhone="+strings[5]+"&userMail="+strings[6];

                //보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
                //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성합니다.

                osw.write(sendMsg);//OutputStreamWriter에 담아 전송합니다.
                osw.flush();
                //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    //jsp에서 보낸 값을 받겠죠?
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                    // 통신이 실패했을 때 실패한 이유를 알기 위해 로그를 찍습니다.
                }

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            //jsp로부터 받은 리턴 값입니다.
            return receiveMsg;

        }

    }


}