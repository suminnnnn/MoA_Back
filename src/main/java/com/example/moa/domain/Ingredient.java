package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="ingredient")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @Column(nullable = false)
    private LocalDate registeredDate;

    @Column(nullable = false)
    private String ingredientImage;

    @Column(nullable = false)
    private String receiptImage;

    @Column(nullable = false)
    private LocalDate purchasedDate;

    @Column(nullable = false)
    private LocalDate expirationDate;
}