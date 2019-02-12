package com.example.groundzero.farewell;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.Date;

public class post extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    EditText description,eulogy;
    int Dyear, age;
    NumberPicker dob;
    Spinner sex;
    TextInputEditText name, dod;
    ArrayAdapter<CharSequence> adapter;
    Button button2;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Date date = new Date();
    DateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
    String dat;
    String n,da,dd,s,de,e;

    public void toast(String message){
        Toast.makeText(post.this, message,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        name = findViewById(R.id.editText4);
        dob = findViewById(R.id.editText9);
        dod = findViewById(R.id.editText6);
        description = (EditText)findViewById(R.id.editText7);
        eulogy = (EditText)findViewById(R.id.editText8);
        button2 = (Button)findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        sex =findViewById(R.id.editText10);

        dob.setMaxValue(2019);
        dob.setMinValue(1900);
        dob.setWrapSelectorWheel(false);
        dob.getMaxValue();


        adapter = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);
        sex.setOnItemSelectedListener(this);


        dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new com.example.groundzero.farewell.DatePicker();
                dialog.show(getSupportFragmentManager(),"Date Picker");
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                n = name.getText().toString();
                da = Integer.toString(dob.getValue());
                dd = dod.getText().toString();
                de = description.getText().toString();
                e = eulogy.getText().toString();
                dat = sdf.format(date);
                age = Dyear - dob.getValue();
                String Sage = Integer.toString(age);
                if(n.equals("") || da.equals("") || dd.equals("") || s.equals("") || de.equals("") || e.equals("") ) {
                    Toast.makeText(post.this, "please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }else{
                   postI d = new postI(n,da,dd,s,de,e,dat,currentUser.getEmail(),Sage);

                     db.collection("obituaries")
                             .document(n)
                            .set(d)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    toast("User created succesfully");
                                }
                            })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                   toast("User created unsuccesfully" + e);
                                 }
                             });


                Intent I = new Intent(post.this,MainActivity.class);
                startActivity(I);
            }}



        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        dod.setText(DateFormat.getDateInstance().format(c.getTime()));
        Dyear = year;
    }
}
