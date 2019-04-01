package com.example.bigbase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;


public class SnsLogin extends AppCompatActivity {

    public static OAuthLoginButton naverLoginButton ;
    public static OAuthLogin naverLoginInstance;
    public static Context context;
    private String OAUTH_CLIENT_ID = "709UBoA8TWpWXMMw3HDc";
    private String OAUTH_CLIENT_SECRET = "qTz6FDFV4P";
    private String OAUTH_CLIENT_NAME = "네이버로 로그인";



    @Override

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.snslogin);
        //context 저장


        initData();
        initView();
    }
        //초기화
        private void initData(){
        context = SnsLogin.this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.showDevelopersLog(true);
        naverLoginInstance.init(
                SnsLogin.this
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME);


    }
        private void initView() {
            //로그인버튼 세팅
            naverLoginButton = findViewById(R.id.button_naverlogin);


            //로그인 핸들러
            OAuthLoginHandler naverLoginHandler = new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {
                    if (success) {//로그인 성공
                        Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show();
                    } else {//로그인 실패
                        String errorCode = naverLoginInstance.getLastErrorCode(context).getCode();
                        String errorDesc = naverLoginInstance.getLastErrorDesc(context);
                        Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                    }
                }

            };


            naverLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        }
}