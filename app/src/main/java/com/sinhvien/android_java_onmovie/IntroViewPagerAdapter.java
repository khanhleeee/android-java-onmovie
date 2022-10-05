package com.sinhvien.android_java_onmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends FragmentStateAdapter {
    public IntroViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OneIntroFragment();
            case 1:
                return new TwoIntroFragment();
            case 2:
                return new ThreeIntroFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
