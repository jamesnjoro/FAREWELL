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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class activity_signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText username, email,password,gender,location;
    private Button butt;
    private Toolbar mtoolbar;
    FirebaseUser user;
    user u;
    FirebaseFirestore db;
    String us,em,pas,ge,lo;

    Button pho;

    private ProgressDialog progressDialog;

    public void toast(String message){
        Toast.makeText(activity_signup.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        username = (EditText)findViewById(R.id.nameI);
        email = (EditText)findViewById(R.id.emailI);
        password = (EditText)findViewById(R.id.passwordI);
        gender = (EditText)findViewById(R.id.genderI);
        location = (EditText)findViewById(R.id.homeLocationI);
        butt = (Button)findViewById(R.id.button);
        db =FirebaseFirestore.getInstance();

        pho = (Button)findViewById(R.id.PhotoUpload);

        pho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });




        progressDialog = new ProgressDialog(this);


        butt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                us=username.getText().toString();
                em = email.getText().toString();
                pas= password.getText().toString();
                ge= gender.getText().toString();
                lo = location.getText().toString();
                progressDialog.setTitle("Creating user");
                progressDialog.setMessage("Account is being created please wait.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

            if(us.equals("") || em.equals("") || pas.equals("") || ge.isEmpty() || lo.isEmpty() ){
                toast("please fill all fields");

            }else{

                u = new user(us,em,ge,lo);



                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            db.collection("users")
                                    .document(em)
                                    .set(u)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            toast("user created successfully");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            toast("user creation failed");
                                        }
                                    });
                            toast("authentication successful");
                            Intent I = new Intent(activity_signup.this,MainActivity.class);
                            startActivity(I);
                            finish();

                        } else {
                            progressDialog.hide();
                            toast("authentication failed");
                        }
                    }
                });


                }

                                // ...
                            }

        }
                        );
    }}
