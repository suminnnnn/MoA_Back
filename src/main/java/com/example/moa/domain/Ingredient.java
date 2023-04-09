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

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    private LocalDate registeredDate;

    @Lob
    @Column(nullable = false)
    private byte[] photo;

    @Lob
    @Column(nullable = false)
    private byte[] receiptPhoto;

    @Column(nullable = false)
    private LocalDate purchasedDate;

    @Column(nullable = false)
    private LocalDate expirationDate;
}
