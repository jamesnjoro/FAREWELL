package com.example.groundzero.farewell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore db;
    DocumentReference documentReference;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<postI> options;
    FirebaseRecyclerAdapter<postI,MyAdapter> adapter;
    RecyclerView recyclerView;
    InternetCheck check;
    TextView nameNav,emailNav;
    FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");
        currentUser = mAuth.getCurrentUser();
        recyclerView= (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        check = new InternetCheck(this);

        if(currentUser==null){
            startScreen();
        }else{
            displaC();
            adapter.notifyDataSetChanged();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        nameNav = (TextView) headerView.findViewById(R.id.userNav);
        emailNav =(TextView) headerView.findViewById(R.id.textView);

        documentReference = db.collection("users").document(currentUser.getEmail());
        emailNav.setText(currentUser.getEmail());

            documentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            nameNav.setText(documentSnapshot.getString("name"));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    nameNav.setText("No name");
                }
            });
        if(check.isConnected()){
            Toast.makeText(MainActivity.this, "connection Successful",
                    Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(MainActivity.this, "Please connect to the internet",
                    Toast.LENGTH_SHORT).show();
        }




    }

    public void startScreen(){
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id =item.getItemId();
        if(item.getItemId()==R.id.logoutButton){
            FirebaseAuth.getInstance().signOut();
            startScreen();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutButton) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {
                FirebaseAuth.getInstance().signOut();
                startScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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


