package com.example.groundzero.farewell;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class memDetailsFragment extends Fragment {
    Memorial m;
    TextView name;
    TextView deathDate;
    TextView eulogy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        m = getArguments().getParcelable("mem");
        View rootView = inflater.inflate(R.layout.detail_layout, container, false);
        name = rootView.findViewById(R.id.detName);
        deathDate = rootView.findViewById(R.id.detDeath);
        eulogy = rootView.findViewById(R.id.detEulogy);
        name.setText(m.getName());
        deathDate.setText(m.getBirth());
        eulogy.setText(m.getEulogy());
        return rootView;
    }
}
