package com.example.e_commerce.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class HomeViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;

    public HomeViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, @NonNull List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        if (fragments == null) {
            throw new IllegalArgumentException("Fragments list cannot be null");
        }
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
