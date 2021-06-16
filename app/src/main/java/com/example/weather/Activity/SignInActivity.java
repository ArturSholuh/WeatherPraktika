package com.example.weather.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weather.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import com.facebook.FacebookSdk;

import java.util.Arrays;


public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int SIGN_IN_GOOGLE = 1;

    CallbackManager mCallbackManager;
    LoginButton loginButton;
    Button login_button_facebook;

    private Button signIn_button_google;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);//Возможность работы оффлайн
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        FacebookSdk.sdkInitialize(SignInActivity.this);

        mAuth = FirebaseAuth.getInstance();

        //Вход с помощью Facebook
        mCallbackManager = CallbackManager.Factory.create();
        login_button_facebook = findViewById(R.id.login_button_facebook);
        login_button_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
            }
        });

        //Вход с помощью Google
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        }
        catch (Exception ex){
            Toast.makeText(this, R.string.try_later, Toast.LENGTH_SHORT).show();
        }

        //Поиск и обработка нажатия кнопки входа Google
        signIn_button_google = findViewById(R.id.btn_signIn_google);
        signIn_button_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, SIGN_IN_GOOGLE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Если пользователь вошел в систему то вызвать следующею активити
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            nextActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_GOOGLE) {
            if(resultCode == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getEmail());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (Exception e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Успешный вход
                            Log.d(TAG, "signInWithCredential:success Google");
                            Toast.makeText(SignInActivity.this, R.string.successful_login, Toast.LENGTH_SHORT).show();
                            nextActivity();
                        } else {
                            // Ошибка входа
                            Log.w(TAG, "signInWithCredential:failure Google", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Успешный вход
                            Log.d(TAG, "signInWithCredential:success Facebook", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.successful_login, Toast.LENGTH_SHORT).show();
                            nextActivity();
                        } else {
                            // Ошибка входа
                            Log.e(TAG, "signInWithCredential:failure Facebook", task.getException());
                            //Если адрес эл. почты зарегистрирован с другого сервиса
                            if(task.getException().getMessage().equals(getString(R.string.login_from_another_service))){
                                Toast.makeText(SignInActivity.this, R.string.login_from_another_service_message, Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(SignInActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void nextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}