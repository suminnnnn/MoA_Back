package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name="birth_date")
    private String birth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column
    private int matchingCount;

    @Column
    private String favoriteFriends;

}
