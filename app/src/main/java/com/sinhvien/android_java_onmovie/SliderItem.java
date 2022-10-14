package com.sinhvien.android_java_onmovie;

public class SliderItem {

    private String title;
    private int backdrop;

    public SliderItem(String title, int backdrop) {
        this.title = title;
        this.backdrop = backdrop;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop(int backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public int getBackdrop() {
        return backdrop;
    }


}
