package com.sinhvien.android_java_onmovie.model;

import androidx.annotation.NonNull;

public class SliderItem {

    private String backdrop;
    private String name;

    @NonNull
    @Override
    public String toString() {
        return "films {" +
                "backdrops = ' " + backdrop + "'"+
                ", name = '" + name + "'"
                + "}";
    }

    public SliderItem() {;
    }

    public SliderItem(String backdrop, String name) {
        this.backdrop = backdrop;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
