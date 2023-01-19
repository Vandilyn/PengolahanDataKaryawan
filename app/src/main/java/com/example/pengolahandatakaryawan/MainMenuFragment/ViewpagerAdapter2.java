package com.example.pengolahandatakaryawan.MainMenuFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class ViewpagerAdapter2 extends FragmentStateAdapter {
    public ViewpagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new ListFragment();
            case 1: return new DataFragment();
            default: return  new ListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
