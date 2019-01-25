package com.example.groundzero.farewell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class post extends AppCompatActivity {

    EditText name,dod,dob,sex,description,eulogy;
    Button button2;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Date date = new Date();
    DateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String dat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        name = (EditText)findViewById(R.id.editText4);
        dob = (EditText)findViewById(R.id.editText9);
        dod = (EditText)findViewById(R.id.editText6);
        sex = (EditText)findViewById(R.id.editText10);
        description = (EditText)findViewById(R.id.editText7);
        eulogy = (EditText)findViewById(R.id.editText8);
        button2 = (Button)findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();





        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n,da,dd,s,de,e;
                n = name.getText().toString();
                da = dob.getText().toString();
                dd = dod.getText().toString();
                s = sex.getText().toString();
                de = description.getText().toString();
                e = eulogy.getText().toString();
                dat = sdf.format(date);

                if(n.equals("") || da.equals("") || dd.equals("") || s.equals("") || de.equals("") || e.equals("") ) {
                    Toast.makeText(post.this, "please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }else{
                   postI d = new postI(n,da,dd,s,de,e,dat,currentUser.getEmail());

                     db.collection("orbituraries")
                             .document(n)
                            .set(d)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(post.this, "User created succesfully",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(post.this, "User created unsuccesfully" + e,
                                             Toast.LENGTH_LONG).show();
                                 }
                             });


                Intent I = new Intent(post.this,MainActivity.class);
                startActivity(I);
            }}



        });
    }
}
