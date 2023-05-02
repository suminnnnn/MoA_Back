package com.example.moa.domain;

import com.example.moa.dto.recruit.RecruitModifyDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private List<Ingredient> ingredients = new ArrayList<>();

    private int maxPeople;

    private int participatePeople;

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

    @Column(name = "recruit_users")
    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL)
    @ElementCollection
    private List<RecruitUser> recruitUsers = new ArrayList<>();


    private List<String> needIngredients = new ArrayList<>();

//    @OneToOne
//    @Column(name = "chat_room_id")
//    private ChatRoom chatRoom;



    public void addParticipatePeople(){
        participatePeople++;
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
