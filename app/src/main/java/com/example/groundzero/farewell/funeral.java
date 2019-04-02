package com.example.groundzero.farewell;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DateFormat;
import java.util.Calendar;


public class funeral extends Fragment {
    TextView funda,timeT,noD;
    EditText location,city,town, mpesa, bank, paypal,gps;
    Button save;
    FirebaseFirestore store,view;
    postI p;
    DocumentReference ref;
    RelativeLayout r;
    String userE;


    private TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String m = ""+0+"" ;
            if(minute < 10){
                m = m+minute;
            }else {
                m= ""+minute+"";
            }
            String t = hourOfDay + ":" + m;
            timeT.setText(t);
        }
    };


    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            funda.setText(DateFormat.getDateInstance().format(c.getTime()));

        }
    };

    private void editActivity(){
        store = FirebaseFirestore.getInstance();
        timeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),time,0,0,true);
                timePickerDialog.show();
            }
        });
        funda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),listener,2019,1,1);
                dialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFuneral,funeralL,time,gps2,city2,town2,mpesa2,bank2,paypal2;
                dateFuneral = funda.getText().toString();
                funeralL = location.getText().toString();
                time = timeT.getText().toString();
                gps2 = gps.getText().toString();
                city2 = city.getText().toString();
                town2 = town.getText().toString();
                mpesa2 = mpesa.getText().toString();
                bank2 = bank.getText().toString();
                paypal2 = paypal.getText().toString();

                if(dateFuneral.isEmpty() || funeralL.isEmpty() || time.isEmpty() || city2.isEmpty() || town2.isEmpty()){
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(gps2.isEmpty()){
                        gps2 = "not set";
                    }
                    if(mpesa2.isEmpty()){
                        mpesa2 = "not set";
                    }
                    if(bank2.isEmpty()){
                        bank2 = "not set";
                    }
                    if(paypal2.isEmpty()){
                        paypal2 = "not set";
                    }

                    funeralClass f = new funeralClass(dateFuneral,funeralL,time,gps2,city2,town2,mpesa2,bank2,paypal2,p.getName(),p.getUser());
                    store.collection("funeral")
                            .document(p.getName())
                            .set(f)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    public void viewActivity(){


        switch (userE){
            case "yes":
                Toast.makeText(getContext(), p.getUser(), Toast.LENGTH_SHORT).show();
                editActivity();
                break;
            case "no":
                r.setVisibility(View.INVISIBLE);
                noD.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), userE + " "+ p.getUser(), Toast.LENGTH_LONG).show();
                break;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.funeral, container, false);
        p = getArguments().getParcelable("orbi");
        userE = getArguments().getString("owner");
        funda = rootView.findViewById(R.id.funDate);
        timeT = rootView.findViewById(R.id.meetingT);
        location =rootView.findViewById(R.id.MeetingP);
        city =rootView.findViewById(R.id.fcity);
        town =rootView.findViewById(R.id.ftown);
        gps =rootView.findViewById(R.id.gpsL);
        mpesa =rootView.findViewById(R.id.mpesa);
        bank =rootView.findViewById(R.id.bank);
        paypal = rootView.findViewById(R.id.paypal);
        save = rootView.findViewById(R.id.Fsave);
        r = rootView.findViewById(R.id.r2);
        noD = rootView.findViewById(R.id.NoD);
        view = FirebaseFirestore.getInstance();
        ref = view.collection("funeral").document(p.getName());
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getString("time") == null){
                            viewActivity();
                        }else{
                            funeralClass fun = documentSnapshot.toObject(funeralClass.class);
                            funda.setText(fun.dateFuneral);
                            location.setText(fun.funeralL);
                            timeT.setText(fun.time);
                            gps.setText(fun.gps);
                            city.setText(fun.city);
                            town.setText(fun.city);
                            mpesa.setText(fun.mpesa);
                            bank.setText(fun.bank);
                            paypal.setText(fun.paypal);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        viewActivity();
                    }
                });

        return rootView;
    }
}
