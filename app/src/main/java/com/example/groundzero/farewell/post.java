package com.example.groundzero.farewell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class post extends AppCompatActivity {

    EditText name,dod,dob,sex,description,eulogy;
    Button button2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n,db,dd,s,d,e;
                n = name.getText().toString();
                db = dob.getText().toString();
                dd = dod.getText().toString();
                s = sex.getText().toString();
                d = description.getText().toString();
                e = eulogy.getText().toString();

                if(n.equals("") || db.equals("") || dd.equals("") || s.equals("") || d.equals("") || e.equals("") ) {
                    Toast.makeText(post.this, "please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }else{

                postI po = new postI(n,db,dd,s,d,e);
                databaseReference.push()
                        .setValue(po);
                Intent I = new Intent(post.this,MainActivity.class);
                startActivity(I);
            }}



        });
    }
}
