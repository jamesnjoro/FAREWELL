package com.example.groundzero.farewell;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button but;
    FirebaseUser currentUser;
    RelativeLayout rel;
    ProgressBar progressBar;
    FadingCircle fadingCircle;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rel.setVisibility(View.VISIBLE);
        }
    };


    public void toast(String message){
        Toast.makeText(LoginActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.spin_kit);
        rel = findViewById(R.id.loginList);
        email =(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        but =(Button)findViewById(R.id.email_sign_in_button);
        fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);



        handler.postDelayed(runnable,2000);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length()>1 && password.getText().toString().equals("")){
                    Intent I = new Intent(LoginActivity.this,activity_signup.class);
                    startActivity(I);


                }
                if(email.getText().toString().equals("") && password.getText().toString().equals("")){

                    toast("please fill in the email address.");
                }if(email.getText().toString().length()>1 && password.getText().toString().length()>1){

                    progressBar.setVisibility(View.VISIBLE);
                    but.setVisibility(View.INVISIBLE);
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            but.setVisibility(View.VISIBLE);
                            progressBar  .setVisibility(View.INVISIBLE);
                            toast(e.getMessage());
                        }
                    });




                }
            }
        });
    }




}
