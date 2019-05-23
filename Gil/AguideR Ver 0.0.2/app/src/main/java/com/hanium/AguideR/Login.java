package com.hanium.AguideR;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

        final EditText id = (EditText) findViewById(R.id.travelid);
        final EditText pw = (EditText) findViewById(R.id.password);
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

        Button communitybtn = (Button)findViewById(R.id.communitybtn);

        communitybtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent communityintent = new Intent(Login.this, Community.class);
                Login.this.startActivity(communityintent);
                finish();
            }
        });
        ///커뮤니티 이동

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

        loginbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = id.getText().toString();
                final String userPassword = pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userID = jsonResponse.getString("userID");
                                String userPassword = jsonResponse.getString("userPassword");
                                Intent intent = new Intent(Login.this, Main.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();


                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
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

                Intent jspregisterintent = new Intent(Login.this, JspLoginRegister.class);
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

}
