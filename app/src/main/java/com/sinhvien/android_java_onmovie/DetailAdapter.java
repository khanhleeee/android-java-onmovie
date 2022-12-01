package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DetailAdapter extends FragmentStateAdapter {
    public DetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TrailerFragment();
            case 1:
                return  new CastFragment();
            case 2:
                return new RecommendedMovieFragment();
        }
        return  null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
