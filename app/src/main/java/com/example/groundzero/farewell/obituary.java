package com.example.groundzero.farewell;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class obituary extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    String owner;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    postI post;
    String em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        post = in.getParcelableExtra("o");
        em = in.getStringExtra("email");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user.getEmail()==post.getUser()){
            owner = "yes";
        }else{
            owner = "no";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obituary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }






    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
          Bundle bundle = new Bundle();
          bundle.putParcelable("orbi",post);
          bundle.putString("owner",owner);
            switch(position){
                case 0:
                    deceased de = new deceased();
                    de.setArguments(bundle);
                    return de;
                case 1:
                    service se = new service();
                   se.setArguments(bundle);
                    return se;
                case 2:
                    funeral fun = new funeral();
                    fun.setArguments(bundle);
                    return fun;
                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
