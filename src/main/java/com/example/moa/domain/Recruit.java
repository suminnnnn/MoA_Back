package com.example.moa.domain;

import com.example.moa.dto.recruit.RecruitModifyDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private Long id;

    private String foodName;

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    private int maxPeople;
    @Column(name = "recruit_date")
    private LocalDate recruitDate;
    // 모임 날짜

    @Column(name = "created_at")
    private LocalDate createdAt;
    //작성 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_email")
    private User writer;

    private String title;

    private String content;

    @ManyToMany(mappedBy = "recruits", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();
    //승인된 참가자

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL)
    private List<RecruitUser> recruitUsers = new ArrayList<>();
    //대기열

    private List<String> needIngredients = new ArrayList<>();

    private String chatRoomId;


    public void addUsers(User user){
        users.add(user);
        user.getRecruits().add(this);
    }
    public Recruit update(RecruitModifyDto modifyDto) {

        this.foodName = modifyDto.getFoodName();
        this.needIngredients = modifyDto.getNeedIngredients();
        this.maxPeople = modifyDto.getMaxPeople();
        this.recruitDate = LocalDate.parse(modifyDto.getRecruitDate());
        this.title = modifyDto.getTitle();
        this.content = modifyDto.getContent();

        return this;
    }
}
