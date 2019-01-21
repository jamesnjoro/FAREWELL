package com.example.groundzero.farewell;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button but;
    FirebaseUser currentUser;
    private Toolbar mtoolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email =(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        but =(Button)findViewById(R.id.email_sign_in_button);
        mtoolbar =(Toolbar)findViewById(R.id.LoginBar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("login");
        progressDialog = new ProgressDialog(this);




        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length()>1 && password.getText().toString().equals("")){
                    Intent I = new Intent(LoginActivity.this,activity_signup.class);
                    startActivity(I);
                    finish();

                }
                if(email.getText().toString().equals("") && password.getText().toString().equals("")){

                    Toast.makeText(LoginActivity.this, "please fill in the email address.",
                            Toast.LENGTH_SHORT).show();
                }if(email.getText().toString().length()>1 && password.getText().toString().length()>1){
                    progressDialog.setTitle("loging in");
                    progressDialog.setMessage("Please as we log you into your account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                progressDialog.hide();
                                Toast.makeText(LoginActivity.this, "incorrect credentials",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




                }
            }
        });
    }



}
