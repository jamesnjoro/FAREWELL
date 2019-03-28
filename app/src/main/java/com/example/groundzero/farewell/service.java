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

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;


public class service extends Fragment {
    private TextView date;
    private TextView startT,stopT;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.service, container, false);
        date = rootView.findViewById(R.id.Sdate);
        startT = rootView.findViewById(R.id.serviceStart);
        stopT = rootView.findViewById(R.id.serviceStop);
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

        return rootView;
    }



}
