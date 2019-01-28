package com.example.groundzero.farewell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class obituary extends AppCompatActivity {
    TextView name,dob,dod,description,eulogy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obituary);
        Intent intent = getIntent();
        postI p = intent.getParcelableExtra("obituary");
        name = findViewById(R.id.nameF);
        dob = findViewById(R.id.dob);
        dod = findViewById(R.id.dod);
        description = findViewById(R.id.description);
        eulogy = findViewById(R.id.eulogy);

        name.setText(p.getName());
        dob.setText(p.getDob());
        dod.setText(p.getDod());
        description.setText(p.getDescription());
        eulogy.setText(p.getEulogy());

    }
}
