package com.example.moa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    private String gender;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "matching_count")
    private int matchingCount;

    @Column(name = "favorite_friends")
    private String favoriteFriends;

    @Column(name = "join_date")
    private LocalDate joinDate;

    public User(String email, String password, String gender, String name, Date birthDate, int matchingCount, String favoriteFriends, LocalDate joinDate) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.birthDate = birthDate;
        this.matchingCount = matchingCount;
        this.favoriteFriends = favoriteFriends;
        this.joinDate = joinDate;
    }

    public User(String email, String password, String gender, String name, Date birthDate)
    {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.birthDate = birthDate;
    }

    public User(){};

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
