package com.hanium.AguideR;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;
import java.net.*;

public class JspLoginRegister extends Activity {

    EditText regid, regpw, regname, regage;
    Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsploginregister);


        regid = (EditText) findViewById(R.id.regid);
        regpw = (EditText) findViewById(R.id.regpw);
        regname = (EditText) findViewById(R.id.regname);
        regage = (EditText) findViewById(R.id.regage);




        loginBtn = (Button) findViewById(R.id.loginbtn);
        registerBtn = (Button) findViewById(R.id.regbtn);
        loginBtn.setOnClickListener(btnListener);
        registerBtn.setOnClickListener(btnListener);

    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://182.210.78.154:8080/ConnectDB.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&userID="+strings[0]+"&userPassword="+strings[1]+"&userName="+strings[2]+"&userAge="+strings[3]+"&type="+strings[4];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginbtn : // 로그인 버튼 눌렀을 경우
                    String userID = regid.getText().toString();
                    String userPassword = regpw.getText().toString();
                    try {
                        String result  = new CustomTask().execute(userID,userPassword,"login").get();
                        if(result.equals("true")) {
                            Toast.makeText(JspLoginRegister.this,"로그인",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JspLoginRegister.this, Main.class);
                            startActivity(intent);
                            finish();
                        } else if(result.equals("false")) {
                            Toast.makeText(JspLoginRegister.this,"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
                            regid.setText("");
                            regpw.setText("");
                        } else if(result.equals("noId")) {
                            Toast.makeText(JspLoginRegister.this,"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
                            regid.setText("");
                            regpw.setText("");
                        }
                    }catch (Exception e) {}
                    break;
                case R.id.regbtn : // 회원가입
                    String useID = regid.getText().toString();
                    String usePassword = regpw.getText().toString();
                    String userName = regname.getText().toString();
                    String userAge = regage.getText().toString();



                    try {
                        String result  = new CustomTask().execute(useID,usePassword,userName,userAge,"register").get();
                        if(result.equals("id")) {
                            Toast.makeText(JspLoginRegister.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            regid.setText("");
                            regpw.setText("");
                        } else if(result.equals("ok")) {
                            regid.setText("");
                            regpw.setText("");
                            regname.setText("");
                            regage.setText("");


                            Toast.makeText(JspLoginRegister.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {}
                    break;
            }
        }
    };
}