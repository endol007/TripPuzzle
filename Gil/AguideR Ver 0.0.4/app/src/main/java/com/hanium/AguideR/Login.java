package com.hanium.AguideR;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class Login extends AppCompatActivity implements OnClickListener{


    private Context mContext;



    private LoginButton btn_facebook_login;
    private Button btn_custom_login;


    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;

    Button findidbtn;
    //로그인 버튼

    //카카오 로그인 콜백 선언
    private SessionCallback sessionCallback;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText loginid = (EditText) findViewById(R.id.travelid);
        final EditText loginpw = (EditText) findViewById(R.id.password);
        Button loginbutton = (Button) findViewById(R.id.loginbtn);
        final Button registerbtn = (Button) findViewById(R.id.registerbtn);

        final Button jspregisterbtn = (Button) findViewById(R.id.jspregisterbtn);

        findidbtn = (Button) findViewById(R.id.findidbtn) ;


        final EditText regid = (EditText) findViewById(R.id.regid);
        final EditText regpw = (EditText) findViewById(R.id.regpw);




        //카카오 로그인 콜백 초기화
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        //앱 실행 시 로그인 토큰이 있으면 자동으로 로그인 수행
        Session.getCurrentSession().checkAndImplicitOpen();

        //커스텀 카카오 로그인 버튼
        Button btnLoginKakao = findViewById(R.id.kakaoLoginButton2);



        Button gpstestbtn = (Button)findViewById(R.id.gpstestbtn);

        gpstestbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gpsintent = new Intent(Login.this, GpsActivity.class);
                Login.this.startActivity(gpsintent);
                finish();
            }
        });
        ///GPS페이지 이동


        Button loginokbtn = (Button)findViewById(R.id.loginokbtn);

        loginokbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maiintent = new Intent(Login.this, Main.class);
                Login.this.startActivity(maiintent);
                finish();
            }
        });
        ///로그인 완료라서 메인 페이지 이동 시범

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = loginid.getText().toString();
                String userPassword = loginpw.getText().toString();


                try {
                    String result;
                    LoginTask task = new LoginTask();
                    result = task.execute(userID, userPassword).get();
                    Log.i("리턴 값", result);

                    if(result.equals("true")){
                        Intent maiintent = new Intent(Login.this, Main.class);
                        Login.this.startActivity(maiintent);

                        finish();

                        //true 받으면 메인 페이지로 이동하기


                    } if(result.equals("false")) {

                        Toast.makeText(getApplicationContext(), "로그인에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();

                    } if(result.equals("noId")) {
                        Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (Exception e) {

                }
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


        jspregisterbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent jspregisterintent = new Intent(Login.this, JspGpsRegister.class);
                Login.this.startActivity(jspregisterintent);
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



        mContext = getApplicationContext();



        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallback();



        btn_facebook_login = (LoginButton) findViewById(R.id.btn_facebook_login);
        btn_facebook_login.setReadPermissions(Arrays.asList("public_profile", "email"));
        btn_facebook_login.registerCallback(mCallbackManager, mLoginCallback);



        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        btn_custom_login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                btn_facebook_login.performClick();

            }

        });


        btnLoginKakao.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, Login.this);
            }
        });


    }



    @Override
    public void onClick(View v) {

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }



    @Override
    protected void onDestroy() {
        //Activity Destroy 시 카카오 로그인 콜백 제거
        //이 코드가 없으면 타 로그인 플랫폼과 연동 시 오류가 발생할 가능성이 높다.
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    //카카오 로그인 콜백
    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() { //세션이 성공적으로 열린 경우
            UserManagement.getInstance().me(new MeV2ResponseCallback() { //유저 정보를 가져온다.
                @Override
                public void onFailure(ErrorResult errorResult) { //유저 정보를 가져오는 데 실패한 경우
                    int result = errorResult.getErrorCode(); //오류 코드를 받아온다.

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) { //클라이언트 에러인 경우: 네트워크 오류
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else { //클라이언트 에러가 아닌 경우: 기타 오류
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) { //세션이 도중에 닫힌 경우
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) { //유저 정보를 가져오는데 성공한 경우
                    String needsScopeAutority = ""; //이메일, 성별, 연령대, 생일 정보 가져오는 권한 체크용

                    if(result.getKakaoAccount().needsScopeAccountEmail()) { //이메일 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + "이메일";
                    }
                    if(result.getKakaoAccount().needsScopeGender()) { //성별 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + ", 성별";
                    }
                    if(result.getKakaoAccount().needsScopeAgeRange()) { //연령대 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + ", 연령대";
                    }
                    if(result.getKakaoAccount().needsScopeBirthday()) { //생일 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + ", 생일";
                    }

                    if(needsScopeAutority.length() != 0) { //거절된 권한이 있는 경우
                        //거절된 권한을 허용해달라는 Toast 메세지 출력
                        if(needsScopeAutority.charAt(0) == ',') {
                            needsScopeAutority = needsScopeAutority.substring(2);
                        }
                        Toast.makeText(getApplicationContext(), needsScopeAutority+"에 대한 권한이 허용되지 않았습니다. 개인정보 제공에 동의해주세요.", Toast.LENGTH_SHORT).show();

                        //회원탈퇴 수행
                        //회원탈퇴에 대한 자세한 내용은 MainActivity의 회원탈퇴 버튼 참고
                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                int result = errorResult.getErrorCode();

                                if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                    Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNotSignedUp() {
                                Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(Long result) { }
                        });
                    } else { //모든 정보를 가져오도록 허락받았다면
                        //MainActivity로 넘어가면서 유저 정보를 같이 넘겨줌
                        Intent intent = new Intent(getApplicationContext(), KaKaoMain.class);
                        intent.putExtra("name", result.getNickname()); //유저 이름(String)
                        intent.putExtra("profile", result.getProfileImagePath()); //유저 프로필 사진 주소(String)

                        if (result.getKakaoAccount().hasEmail() == OptionalBoolean.TRUE)
                            intent.putExtra("email", result.getKakaoAccount().getEmail()); //이메일이 있다면 -> 이메일 값 넘겨줌(String)
                        else
                            intent.putExtra("email", "none"); //이메일이 없다면 -> 이메일 자리에 none 집어넣음.
                        if (result.getKakaoAccount().hasAgeRange() == OptionalBoolean.TRUE)
                            intent.putExtra("ageRange", result.getKakaoAccount().getAgeRange().getValue()); //연령대 정보 있다면 -> 연령대 정보를 String으로 변환해서 넘겨줌
                        else
                            intent.putExtra("ageRange", "none");
                        if (result.getKakaoAccount().hasGender() == OptionalBoolean.TRUE)
                            intent.putExtra("gender", result.getKakaoAccount().getGender().getValue()); //성별 정보가 있다면 -> 성별 정보를 String으로 변환해서 넘겨줌
                        else
                            intent.putExtra("gender", "none");
                        if (result.getKakaoAccount().hasBirthday() == OptionalBoolean.TRUE)
                            intent.putExtra("birthday", result.getKakaoAccount().getBirthday()); //생일 정보가 있다면 -> 생일 정보를 String으로 변환해서 넘겨줌
                        else
                            intent.putExtra("birthday", "none");

                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) { //세션을 여는 도중 오류가 발생한 경우 -> Toast 메세지를 띄움.
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    class LoginTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @SuppressLint("WrongThread")
        @Override
        // doInBackground의 매개값이 문자열 배열인데요. 보낼 값이 여러개일 경우를 위해 배열로 합니다.
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://182.210.78.154:8080/Login.jsp");//보낼 jsp 주소를 ""안에 작성합니다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&userID="+strings[0]+"&userPassword="+strings[1];

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
