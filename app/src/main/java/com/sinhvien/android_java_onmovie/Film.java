package com.sinhvien.android_java_onmovie;

import java.io.Serializable;

public class Film implements Serializable {
    public String name;
    public String backdrop;
    public String country;
    public String desc;
    public int limitedAge;
    public String poster;
    public String id;

    public Film () {}

    public Film (String name, String backdrop, String country,
                 String desc, int limitedAge, String poster, String filmKey){
        this.name = name;
        this.backdrop = backdrop;
        this.country = country;
        this.desc = desc;
        this.limitedAge = limitedAge;
        this.poster = poster;
        this.id = filmKey;

    }

    public Film (String name, String backdrop){
        this.name = name;
        this.backdrop = backdrop;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameFilm) {
        this.name= name;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getLimitedAge() {
        return limitedAge;
    }

    public void setLimitedAge(Integer limitedAge) {
        this.limitedAge = limitedAge;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getFilmKey() {
        return id;
    }

    public void setFilmKey(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", country='" + country + '\'' +
                ", desc='" + desc + '\'' +
                ", limitedAge=" + limitedAge +
                ", poster='" + poster + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}


