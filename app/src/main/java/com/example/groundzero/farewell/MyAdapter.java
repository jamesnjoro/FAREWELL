package com.example.groundzero.farewell;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.ViewHolder {
    TextView n,dd,db;
    public MyAdapter(@NonNull View itemView) {
        super(itemView);
        n = (TextView)itemView.findViewById(R.id.textView15);
        dd= (TextView)itemView.findViewById(R.id.textView16);
        db= (TextView)itemView.findViewById(R.id.textView17);
    }
}
