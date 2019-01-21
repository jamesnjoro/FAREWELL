package com.example.groundzero.farewell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<postI> options;
    FirebaseRecyclerAdapter<postI,MyAdapter> adapter;
    RecyclerView recyclerView;
    InternetCheck check;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mtoolbar = (Toolbar)findViewById(R.id.mainActionBar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Farewell");
        recyclerView= (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        check = new InternetCheck(this);

        if(check.isConnected()){
            Toast.makeText(MainActivity.this, "Please connect to the internet",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "connection Successful",
                    Toast.LENGTH_SHORT).show();
        }


        if(currentUser==null){
           startScreen();
        }else{
            displaC();
            adapter.notifyDataSetChanged();

        }

        }

        public void startScreen(){
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.logoutButton){
            FirebaseAuth.getInstance().signOut();
            startScreen();
        }

        return true;
    }

    private void displaC() {
         options =
                new FirebaseRecyclerOptions.Builder<postI>()
                .setQuery(databaseReference,postI.class)
                .build();
         adapter =
                new FirebaseRecyclerAdapter<postI, MyAdapter>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull postI model) {
                        holder.n.setText(model.getName());
                        holder.db.setText(model.getDob());
                        holder.dd.setText(model.getDod());
                    }

                    @NonNull
                    @Override
                    public MyAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemview = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item,viewGroup,false);
                        return new MyAdapter(itemview);
                    }
                };
         adapter.startListening();
         recyclerView.setAdapter(adapter);
    }

    public void add(View view){
        Intent I = new Intent(MainActivity.this,post.class);
        startActivity(I);
    }
    @Override

    public void onStart() {
        super.onStart();


        // Check if user is signed in (non-null) and update UI accordingly.
        ValueEventListener postListener = new ValueEventListener() {
            private String TAG;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                postI post = dataSnapshot.getValue(postI.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        databaseReference.addValueEventListener(postListener);


    }
    @Override
    protected void onStop() {
        super.onStop();

    }




}
