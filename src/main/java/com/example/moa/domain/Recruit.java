package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recruit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    @ElementCollection
    private List<String> ingredients;

    private int maxPeople;

    @Column(name = "recruit_date")
    private String recruitDate;
    // 모임 날짜

    @Column(name = "created_at")
    private String createdAt;
    //작성 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_email")
    private User writer;

    private String title;

    private String content;
}
