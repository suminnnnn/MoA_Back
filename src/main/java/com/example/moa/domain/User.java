package com.example.moa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "matching_count")
    private int matchingCount;

    @Column(name = "favorite_friends")
    private String favoriteFriends;

    @Column(name = "join_date")
    private LocalDate joinDate;

    public User(String email,String password, String gender, String name, int age) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.joinDate = LocalDate.now();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMatchingCount() {
        return matchingCount;
    }

    public void setMatchingCount(int matchingCount) {
        this.matchingCount = matchingCount;
    }

    public String getFavoriteFriends() {
        return favoriteFriends;
    }

    public void setFavoriteFriends(String favoriteFriends) {
        this.favoriteFriends = favoriteFriends;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
}
