package com.example.groundzero.farewell;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;


public class activity_signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private EditText username, email,password,location;
    Spinner gender;
    ArrayAdapter<CharSequence> adapter;
    private Button butt;
    FirebaseUser user;
    user u;
    FirebaseFirestore db;
    String us,em,pas,ge,lo,dppath;
    FirebaseStorage storage;
    StorageReference store;
    ImageView view;
    Uri uri;

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
        gender = findViewById(R.id.genderI);
        username = (EditText)findViewById(R.id.nameI);
        email = (EditText)findViewById(R.id.emailI);
        password = (EditText)findViewById(R.id.passwordI);
        location = (EditText)findViewById(R.id.homeLocationI);
        butt = (Button)findViewById(R.id.button);
        db =FirebaseFirestore.getInstance();
        storage= FirebaseStorage.getInstance();
        pho = (Button)findViewById(R.id.PhotoUpload);
        store= storage.getReference("users_dp");
        view = findViewById(R.id.img);

        adapter = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);


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
                lo = location.getText().toString();


            if(us.equals("") || em.equals("") || pas.equals("") || lo.isEmpty() ){
                toast("please fill all fields");

            }else{
                progressDialog.setTitle("Creating user");
                progressDialog.setMessage("Account is being created please wait.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            if(uri!=null){
                                dppath = String.valueOf(currentTimeMillis()) + "." + getFileExtension(uri);
                                final  StorageReference file = store.child(dppath);
                                file.putFile(uri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                toast("upload successful");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                toast("upload was unsuccessful");
                                            }
                                        });
                            }else
                            {

                                    dppath = "noimage.png";

                            }

                            u = new user(us,em,ge,lo,dppath);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode==RESULT_OK){
            uri = data.getData();
            view.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ge = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
