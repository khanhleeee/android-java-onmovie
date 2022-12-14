package com.sinhvien.android_java_onmovie.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Film implements Comparable<Film>, Serializable {

    // Serializable -- chuyển đổi kiểu dữ liệu không phải kiểu 7 dữ liệu căn bản
    // Comparable<> so sánh, sắp xếp theo thứ tự

    String id, name, backdrop, poster, country, desc;
    List<String> film_casts, film_genres, trailers, videos;
    int limitedAge, month, year;

    public Film() {
    }

    public Film(String id, String name, String backdrop, String poster, String country, String desc, List<String> film_casts, List<String> film_genres, List<String> trailers, List<String> videos, int limitedAge, int month, int year) {
        this.id = id;
        this.name = name;
        this.backdrop = backdrop;
        this.poster = poster;
        this.country = country;
        this.desc = desc;
        this.film_casts = film_casts;
        this.film_genres = film_genres;
        this.trailers = trailers;
        this.videos = videos;
        this.limitedAge = limitedAge;
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", poster='" + poster + '\'' +
                ", country='" + country + '\'' +
                ", desc='" + desc + '\'' +
                ", film_casts=" + film_casts +
                ", film_genres=" + film_genres +
                ", trailers=" + trailers +
                ", videos=" + videos +
                ", limitedAge=" + limitedAge +
                ", month=" + month +
                ", year=" + year +
                '}';
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFilm_casts(List<String> film_casts) {
        this.film_casts = film_casts;
    }

    public void setFilm_genres(List<String> film_genres) {
        this.film_genres = film_genres;
    }

    public void setTrailers(List<String> trailers) {
        this.trailers = trailers;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public void setLimitedAge(int limitedAge) {
        this.limitedAge = limitedAge;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public String getCountry() {
        return country;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getFilm_casts() {
        return film_casts;
    }

    public List<String> getFilm_genres() {
        return film_genres;
    }

    public List<String> getTrailers() {
        return trailers;
    }

    public List<String> getVideos() {
        return videos;
    }

    public int getLimitedAge() {
        return limitedAge;
    }

    @Override
    public int compareTo(Film film) {
        return 0;
    }
}
