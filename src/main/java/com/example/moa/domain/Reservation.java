package com.example.moa.domain;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reservation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @Column(name="reservation_date")
    private LocalDate reservationDate;

    @Column(name="reservation_time")
    private LocalTime reservationTime;

    @Column(name="reservation_location")
    private String reservationLocation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reservation_user",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_email"))
    private List<User> users = new ArrayList<>();

}
