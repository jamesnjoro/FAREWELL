package com.example.groundzero.farewell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.groundzero.farewell.MyAppGlideModule;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    CollectionReference coll;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore dbR;
    FirebaseFirestore db;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    InternetCheck check;
    TextView nameNav,emailNav;
    FirebaseUser currentUser;
    MyAdapter adapter;
    FirebaseStorage storage;
    StorageReference store;
    String email;

    public void toast(String message){
        Toast.makeText(MainActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        dbR = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
        check = new InternetCheck(this);
        coll = dbR.collection("obituaries");
        storage = FirebaseStorage.getInstance();
        final ImageView image;

        if(currentUser==null){
            startScreen();
        }else{
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
            image = headerView.findViewById(R.id.imageView);


            emailNav.setText(currentUser.getEmail());
            documentReference = db.collection("users").document(currentUser.getEmail());

            documentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            nameNav.setText(documentSnapshot.getString("name"));
                            store = storage.getReference("users_dp/" + documentSnapshot.getString("path"));
                            GlideApp.with(MainActivity.this)
                                    .load(store)
                                    .placeholder(R.drawable.noimage)
                                    .into(image);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    nameNav.setText("No name");
                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,
                    new orbituaryFragment()).commit();

        }







    }

    public void startScreen(){
        Intent i = new Intent(MainActivity.this,start.class);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,
                    new orbituaryFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,
                    new memorialFragment()).commit();

        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(MainActivity.this,settings.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
                FirebaseAuth.getInstance().signOut();
                startScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void add(View view){
        Intent I = new Intent(MainActivity.this,post.class);
        startActivity(I);
    }
}


