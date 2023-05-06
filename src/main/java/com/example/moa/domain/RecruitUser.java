package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "recruit_user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = " ingredient_id")
    private List<Ingredient> ingredients;
}