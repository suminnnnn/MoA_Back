package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "birth")
    private String birth;

    @Column(name = "matching_count")
    private int matchingCount;

    @Column(name = "favorite_friends")
    private String favoriteFriends;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Recruit> MyRecruits = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recruit_participate",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
    private List<Recruit> recruits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients =new ArrayList<>();

    private List<String> chatRoomId;
}