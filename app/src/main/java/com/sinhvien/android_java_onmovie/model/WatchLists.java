package com.sinhvien.android_java_onmovie.model;

public class WatchLists {

    String id, name;

    public WatchLists (){}

    public WatchLists(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WatchLists{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
