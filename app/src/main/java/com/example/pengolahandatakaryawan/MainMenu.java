package com.example.pengolahandatakaryawan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.pengolahandatakaryawan.Data.Karyawan;
import com.example.pengolahandatakaryawan.MainMenuFragment.ViewpagerAdapter2;
import com.google.android.material.tabs.TabLayout;

public class MainMenu extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewpagerAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        adapter = new ViewpagerAdapter2(this);
        viewPager2.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}