package com.example.groundzero.farewell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class memorialView extends AppCompatActivity {
    private BottomNavigationView navView;
    private memDetailsFragment details = new memDetailsFragment();
    private memPhotoFragment photo = new memPhotoFragment();
    private memStoryFragment story = new memStoryFragment();
    private FrameLayout lay;
    private Memorial m;
    private FirebaseStorage store;
    private StorageReference storage;
    private ImageView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = FirebaseStorage.getInstance();
        setContentView(R.layout.memorial);
        Intent in = getIntent();
        m = in.getParcelableExtra("m");
        lay = findViewById(R.id.memFrag);
        navView = findViewById(R.id.membot);
        view = findViewById(R.id.dpic);
        storage = store.getReference("deceased_pics/" + m.getPhoto());
        GlideApp.with(memorialView.this)
                .load(storage)
                .placeholder(R.drawable.noimage)
                .into(view);



        getSupportFragmentManager().beginTransaction().replace(R.id.memFrag,details).commit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mem",m);
        details.setArguments(bundle);
        photo.setArguments(bundle);
        story.setArguments(bundle);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.details:
                        setLayout(details);
                        return true;
                    case R.id.stories:
                        setLayout(story);
                        return true;
                    case R.id.photos:
                        setLayout(photo);
                        return true;
                        default:
                            return false;
                }
            }
        });
    }

    private void setLayout(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.memFrag,frag).commit();

    }

}
