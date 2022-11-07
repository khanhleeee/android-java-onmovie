package com.sinhvien.android_java_onmovie.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Film implements Comparable<Film>, Serializable {

    // Serializable -- chuyển đổi kiểu dữ liệu không phải kiểu 7 dữ liệu căn bản
    // Comparable<> so sánh, sắp xếp theo thứ tự

    String id, name, backdrop, poster, country, desc;
    List<String> film_casts, film_genres, keyword, trailers, videos;
    int limitedAge;

    public Film() {
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
                ", keyword=" + keyword +
                ", trailers=" + trailers +
                ", videos=" + videos +
                ", limitedAge=" + limitedAge +
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

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    @Override
    public int compareTo(Film film) {
        return 0;
    }
}
