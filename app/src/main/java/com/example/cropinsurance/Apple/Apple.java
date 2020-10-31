package com.example.cropinsurance.Apple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cropinsurance.R;
import com.example.cropinsurance.cropDetails;
import com.example.cropinsurance.dashboard;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Apple extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private appleAbout appleAbout;
    private appleAttack appleAttack;
    private appleAdvise appleAdvise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tablayout);


        appleAbout = new appleAbout();
        appleAttack = new appleAttack();
        appleAdvise = new appleAdvise();

        tabLayout.setupWithViewPager(viewPager);



        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(appleAbout);
        viewPagerAdapter.addFragment(appleAttack);
        viewPagerAdapter.addFragment(appleAdvise);

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setText("About");
        tabLayout .getTabAt(1).setText("Attack");
        tabLayout .getTabAt(2).setText("Advise");

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });


    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), dashboard.class));
        finish();
    }

    private class  ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);

        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    private void back() {
        this.onBackPressed();
    }
}
