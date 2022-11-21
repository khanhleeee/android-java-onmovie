package com.sinhvien.android_java_onmovie.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    public String email;
    public String nickname;
    public String password;
    List<String> watch_lists;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", watch_lists=" + watch_lists +
                '}';
    }

    public List<String> getWatch_lists() {
        return watch_lists;
    }

    public void setWatch_lists(List<String> watch_lists) {
        this.watch_lists = watch_lists;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
