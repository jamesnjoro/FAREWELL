package com.example.groundzero.farewell;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class settings extends AppCompatActivity {
    FirebaseStorage storage;
    FirebaseFirestore db;
    DocumentReference ref;
    FirebaseUser u;
    FirebaseAuth auth;
    ImageView view;
    TextView name;
    user pic;
    String username;
    RecyclerView obituary, memorial, tribute;
    settingObituary adapter;
    settingMemorial madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();
        view= findViewById(R.id.head);
        name = findViewById(R.id.settingName);
        ref = db.collection("users").document(u.getEmail());
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        pic = documentSnapshot.toObject(user.class);
                        StorageReference store = storage.getReference("users_dp/"+ pic.getPath());
                        GlideApp.with(settings.this)
                                .load(store)
                                .into(view);
                        name.setText(pic.getName());
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        obituary=findViewById(R.id.settingObituaries);
        CollectionReference coll = db.collection("obituaries");
        Query q = coll.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<postI> options = new FirestoreRecyclerOptions.Builder<postI>()
                .setQuery(q,postI.class)
                .build();
        adapter = new settingObituary(options);
        obituary.setAdapter(adapter);adapter.startListening();

        obituary.setLayoutManager(new LinearLayoutManager(settings.this));




        memorial= findViewById(R.id.settingMemorials);
        CollectionReference col = db.collection("memorials");
        Query p = col.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Memorial> option = new FirestoreRecyclerOptions.Builder<Memorial>()
                .setQuery(p,Memorial.class)
                .build();
        madapter = new settingMemorial(option);
        memorial.setAdapter(madapter);
        memorial.setLayoutManager(new LinearLayoutManager(settings.this));

    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
        madapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
        madapter.stopListening();
    }
}
