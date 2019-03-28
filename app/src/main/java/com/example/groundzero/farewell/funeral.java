package com.example.groundzero.farewell;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;


public class funeral extends Fragment {
    TextView funda,timeT;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.funeral, container, false);
        funda = rootView.findViewById(R.id.funDate);
        timeT = rootView.findViewById(R.id.meetingT);
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
      
        return rootView;
    }
}
