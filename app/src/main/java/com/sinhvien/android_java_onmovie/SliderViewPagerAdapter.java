package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class SliderViewPagerAdapter extends FragmentStateAdapter {

    List<SliderItem> sliderItems;

    public SliderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.sliderItems = intDatas();
    }

    private List<SliderItem> intDatas() {
        SliderItem item1 = new SliderItem("Chúa tể những chiếc nhẫn 1", R.drawable.backdrop1);
        SliderItem item2 = new SliderItem("Chúa tể những chiếc nhẫn 2", R.drawable.backdrop1);
        SliderItem item3 = new SliderItem("Chúa tể những chiếc nhẫn 3", R.drawable.backdrop1);

        List<SliderItem> list = new ArrayList<SliderItem>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        return list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        SliderItem sliderItem = sliderItems.get(position);
        return new SliderFragment(sliderItem);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
}
