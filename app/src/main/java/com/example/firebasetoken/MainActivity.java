package com.example.firebasetoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String token;
    private String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        createUser("BLUETREE@gmail.com","1234567");
        //getUserId();
    }

    private void createUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        id = task.getResult().getUser().getUid();
                        getToken(id);
                    }
                });
    }

    private void getUserId(){
        id = mAuth.getCurrentUser().getUid();
       mAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                task.getResult().getExpirationTimestamp();
                Log.i("expiration", "getUserId: "+task.getResult().getToken());
            }
        });
        //getToken(id);
    }

    private void getToken(String id) {
        RetrofitClient.getInstance().getApi().getToken(id)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("created", "onResponse: " +response.body().toString());
                        token = response.body().toString();
                        signeWithToken(token);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    private void signeWithToken(String token) {
        mAuth.signInWithCustomToken(token)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("created", "signInWithCustomToken:success");
                            mAuth.getCurrentUser().getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            Log.d("created", "onComplete: " + task.getResult().getToken());
                                            String idToken = task.getResult().getToken();
                                            verifyToken(idToken);
                                        }
                                    });

                        }
                    }
                });

    }

    private void verifyToken(String idToken) {

        RetrofitClient.getInstance().getApi().verifytoken(idToken)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("created", "" + response.body().toString());
                        Log.d("created", "" + response.errorBody());
                        Log.d("created", "" + response.isSuccessful());
                        Log.d("created", "" + response.code() +" message " +response.message());

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("created", "" + t.getMessage()+ " "+t.getStackTrace()+ " "+t.getCause());

                    }
                });

    }
}
