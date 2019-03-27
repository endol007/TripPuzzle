package com.example.tripuzzle.googlelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView userEmail;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmail = (TextView) findViewById(R.id.userEmail);
        // 로그인 이후에 얻어 올 사용자의 데이터를 정의합니다.
        // 이 예제에서는 이메일(Email) 정보를 얻어옵니다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // 구글 로그인 클라이언트 객체를 초기화합니다.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void googleLogin(View view) {
        // 로그인 인텐트를 실행하여 사용자가 로그인을 할 수 있도록 합니다.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        // 로그인 인텐트가 종료되면, 그 결과를 처리합니다.
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("정보", "Connection Failed");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // 로그인 인텐트가 꺼진 이후에, 로그인 결과를 처리합니다.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // 로그인이 성공적으로 이루어 진 경우 인증된 UI를 보여줍니다.
            updateUI(account);
        } catch (ApiException e) {
            // 오류가 발생한 경우 API 예외 상태 코드를 통해 오류 정보를 확인할 수 있습니다.
            Log.d("정보", "Sign In Result: Failed Code = " + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if(account != null) {
            userEmail.setText(account.getEmail());
        }
        else {
            userEmail.setText("NULL");
        }
    }
}
