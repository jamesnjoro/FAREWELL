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

public class orbituaryFragment extends Fragment {

    RecyclerView recyclerView;
    MyAdapter adapter;
    CollectionReference coll;
    FirebaseFirestore dbR;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.orbi, container, false);
        recyclerView= rootView.findViewById(R.id.recycler);
        dbR = FirebaseFirestore.getInstance();
        coll = dbR.collection("obituaries");
        Query q = coll.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<postI> options = new FirestoreRecyclerOptions.Builder<postI>()
                .setQuery(q,postI.class)
                .build();
        adapter = new MyAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnclickListener(new MyAdapter.onclickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                postI p = documentSnapshot.toObject(postI.class);
                Intent in = new Intent(getActivity(),obituary.class);
                in.putExtra("o", p);
                startActivity(in);
            }
        });

        return rootView;
    }
    public void add(View view){
        Intent I = new Intent(getContext(),post.class);
        startActivity(I);
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
