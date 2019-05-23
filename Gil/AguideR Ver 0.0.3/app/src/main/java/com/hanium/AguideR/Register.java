package com.hanium.AguideR;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class Register extends AppCompatActivity implements OnClickListener{


    Button registerbtn;
    //로그인 버튼

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        final EditText regid = (EditText) findViewById(R.id.regid);
        final EditText regpw = (EditText) findViewById(R.id.regpw);
        final EditText regname = (EditText) findViewById(R.id.regname);
        final EditText regage = (EditText) findViewById(R.id.regage);

        registerbtn = (Button) findViewById(R.id.regbtn);



        Button regtolbtn = (Button) findViewById(R.id.regtolbtn);

        regtolbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regtolintent = new Intent(Register.this, Login.class);
                Register.this.startActivity(regtolintent);
                finish();
            }
        });
        //로그인 페이지 이동 버튼







        registerbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = regid.getText().toString();
                String userPassword = regpw.getText().toString();
                String userName = regname.getText().toString();
                int userAge = Integer.parseInt(regage.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(Register.this, Login.class);
                                Register.this.startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}

