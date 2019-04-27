package com.example.groundzero.farewell;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class memStoryFragment extends Fragment {
    EditText story;
    Button save,cancel;
    Memorial m;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();
    String dat;
    FirebaseFirestore db;
    CollectionReference collect;
    Query phot;
    storyAdapter adapter;
    RecyclerView recyclerView;
    Dialog storytell;
    TextView click;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storytell = new Dialog(getActivity());
        storytell.setContentView(R.layout.add_story);
        currentUser = auth.getCurrentUser();
        m = getArguments().getParcelable("mem");
        View rootView = inflater.inflate(R.layout.story_layout, container, false);
        recyclerView = rootView.findViewById(R.id.storyRecycler);
        cancel = storytell.findViewById(R.id.photocancell);
        collect = db.collection("story");
        click = rootView.findViewById(R.id.storytel);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storytell.show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storytell.dismiss();
            }
        });
        phot =collect.whereEqualTo("deceased",m.getName());
        FirestoreRecyclerOptions<story> options = new FirestoreRecyclerOptions.Builder<story>()
                .setQuery(phot,story.class)
                .build();
        adapter = new storyAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        story = storytell.findViewById(R.id.storytell);
        save = storytell.findViewById(R.id.savestory);
        cancel = storytell.findViewById(R.id.photocancell);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dat = sdf.format(date);
                story s = new story(m.getName(),currentUser.getEmail(),dat,story.getText().toString());
                db.collection("story")
                        .document(String.valueOf(currentTimeMillis()))
                        .set(s)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "uploaded", Toast.LENGTH_SHORT).show();
                                storytell.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                                storytell.dismiss();
                            }
                        });
            }
        });

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
