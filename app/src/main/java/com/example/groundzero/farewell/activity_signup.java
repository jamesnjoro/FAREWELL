package com.example.groundzero.farewell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class activity_signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText username, email,password;
    private Button butt;
    private Toolbar mtoolbar;
    FirebaseUser user;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        username = (EditText)findViewById(R.id.editText);
        email = (EditText)findViewById(R.id.editText2);
        password = (EditText)findViewById(R.id.editText3);
        butt = (Button)findViewById(R.id.button);
        mtoolbar =(Toolbar)findViewById(R.id.RegisterBar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Register");

        progressDialog = new ProgressDialog(this);


        butt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            if(username.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("") ){
                Toast.makeText(activity_signup.this, "please fill all fields",
                        Toast.LENGTH_SHORT).show();

            }else{
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.setTitle("Creating user");
                        progressDialog.setMessage("Account is being created please wait.");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(activity_signup.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            Intent I = new Intent(activity_signup.this,MainActivity.class);
                            startActivity(I);
                            finish();

                        } else {
                            progressDialog.hide();
                            Toast.makeText(activity_signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                }

                                // ...
                            }

        }
                        );
    }}
