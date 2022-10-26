package com.sinhvien.android_java_onmovie.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sinhvien.android_java_onmovie.intro.OneIntroFragment;
import com.sinhvien.android_java_onmovie.intro.ThreeIntroFragment;
import com.sinhvien.android_java_onmovie.intro.TwoIntroFragment;

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
