package com.example.moa.domain;

import com.example.moa.dto.RecruitModifyDto;
import com.example.moa.dto.RecruitRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Long recruitId;

    private String foodName;

    @ElementCollection
    private List<Ingredient> ingredients;

    private int maxPeople;

    private int participatePeople;

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

    @Column(name = "recruit_users")
    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL)
    @ElementCollection
    private List<RecruitUser> recruitUsers = new ArrayList<>();

    public void addParticipatePeople(){
        participatePeople++;
    }
    public Recruit update(RecruitModifyDto modifyDto) {
        this.foodName = modifyDto.getFoodName();
        this.ingredients = modifyDto.getIngredients();
        this.maxPeople = modifyDto.getMaxPeople();
        this.recruitDate = modifyDto.getRecruitDate();
        this.title = modifyDto.getTitle();
        this.content = modifyDto.getContent();

        return this;
    }
}
