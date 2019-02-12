package com.example.groundzero.farewell;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class deceased extends Fragment {
    postI p;
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        p = getArguments().getParcelable("orbi");
        View rootView = inflater.inflate(R.layout.deceased, container, false);
        text = rootView.findViewById(R.id.test);
        text.setText(p.getName());
        return rootView;
    }



}
