package com.example.groundzero.farewell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class memorialFragment extends Fragment {

    RecyclerView recyclerView;
    memAdapter adapter;
    CollectionReference coll;
    FirebaseFirestore dbR;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mem, container, false);
        recyclerView= rootView.findViewById(R.id.memrecycler);
        dbR = FirebaseFirestore.getInstance();
        coll = dbR.collection("memorials");
        Query q = coll.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Memorial> options = new FirestoreRecyclerOptions.Builder<Memorial>()
                .setQuery(q,Memorial.class)
                .build();
        adapter = new memAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter.setOnclickListener(new memAdapter.onclickListener() {
        @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Memorial m = documentSnapshot.toObject(Memorial.class);
                Intent in = new Intent(getActivity(),memorialView.class);
                in.putExtra("m", m);
                startActivity(in);
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
