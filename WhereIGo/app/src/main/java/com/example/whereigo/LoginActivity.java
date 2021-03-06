package com.example.whereigo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private ImageButton btn_google;
    private FirebaseAuth auth;
    private GoogleApiClient googleApiClient;
    private static final int RED_SIGN_GOOGLE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        GoogleSignInOptions googoleSigninOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail() // email addresses??? ?????????
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                        Toast.makeText(LoginActivity.this, "connection failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googoleSigninOptions)
                .build();

        auth=FirebaseAuth.getInstance();
        btn_google=findViewById(R.id.googleLoginBtn);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RED_SIGN_GOOGLE);
            }
        });

        signOut2();
    }

    // ?????? ????????? ?????? ??? ?????? ????????? ?????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RED_SIGN_GOOGLE) {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {   // ?????? ????????? ???????????????...
                // ?????? ????????? ?????? ?????? ??????.
                GoogleSignInAccount account = result.getSignInAccount();
                resultLogin(account);
            }
        }
    }

    // ????????? ?????? ??? ?????? ??????
    private void resultLogin(GoogleSignInAccount account) {

        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), GenderActivity.class);
                    intent.putExtra("nickname", account.getDisplayName());
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResutl) {

    }

    public void signOut2() {
        googleApiClient.connect();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override
            public void onConnected(@Nullable Bundle bundle) {
                auth.signOut();
                if (googleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            /*
                            if (status.isSuccess()) {
                                setResult(ResultCode.SIGN_OUT_SUCCESS);
                            } else {
                                setResult(ResultCode.SIGN_OUT_FAIL);
                            }
                            finish();
                            */

                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                //setResult(ResultCode.SIGN_OUT_FAIL);
                finish();
            }
        });
    }
}
