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


public class service extends Fragment {
    private TextView date,startT,stopT,noD;
    private Button button;
    private EditText city,town,street,building,gps;
    postI p;
    FirebaseFirestore store,view;
    DocumentReference ref;
    RelativeLayout r;
    String userE;



    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            date.setText(DateFormat.getDateInstance().format(c.getTime()));

        }
    };

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
            startT.setText(t);
        }
    };

    private TimePickerDialog.OnTimeSetListener time2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String m = ""+0+"" ;
            if(minute < 10){
                m = m+minute;
            }else {
                m= ""+minute+"";
            }
            String t = hourOfDay + ":" + m;
            stopT.setText(t);
        }
    };
    public void editActivity(){
        store = FirebaseFirestore.getInstance();
        stopT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),time2,0,0,true);
                timePickerDialog.show();
            }
        });
        startT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),time,0,0,true);
                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),listener,2019,1,1);
                dialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateS,TimeStart,TimeStop,city1,town1,street1,building1,deceasedN;
                dateS = date.getText().toString();
                TimeStart = startT.getText().toString();
                TimeStop = stopT.getText().toString();
                city1 = city.getText().toString();
                town1 = town.getText().toString();
                street1 = street.getText().toString();
                building1 = building.getText().toString();
                deceasedN = p.getName();

                if(dateS.isEmpty() || TimeStart.isEmpty() || TimeStop.isEmpty() || city1.isEmpty() || town1.isEmpty() || street1.isEmpty() || building1.isEmpty() || deceasedN.isEmpty()){
                    Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    serviceClass s = new serviceClass(dateS,TimeStart,TimeStop,city1,town1,street1,building1,deceasedN,p.getUser(),gps.getText().toString());
                    store.collection("service")
                            .document(deceasedN)
                            .set(s)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
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
                break;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.service, container, false);
        p = getArguments().getParcelable("orbi");
        userE = getArguments().getString("owner");
        date = rootView.findViewById(R.id.Sdate);
        startT = rootView.findViewById(R.id.serviceStart);
        stopT = rootView.findViewById(R.id.serviceStop);
        button = rootView.findViewById(R.id.saves);
        city = rootView.findViewById(R.id.city);
        town = rootView.findViewById(R.id.town);
        street= rootView.findViewById(R.id.street);
        building = rootView.findViewById(R.id.building);
        gps = rootView.findViewById(R.id.gpsS);
        view = FirebaseFirestore.getInstance();
        noD = rootView.findViewById(R.id.noD);
        r = rootView.findViewById(R.id.view);
        ref = view.collection("service").document(p.getName());
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getString("dateS") == null){
                            viewActivity();
                        }else {
                            serviceClass sun = documentSnapshot.toObject(serviceClass.class);
                            date.setText(sun.getDateS());
                            startT.setText(sun.getTimeStart());
                            stopT.setText(sun.getTimeStop());
                            gps.setText(sun.getGps());
                            city.setText(sun.getCity());
                            town.setText(sun.getTown());
                            street.setText(sun.getStreet());
                            building.setText(sun.getBuilding());
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
