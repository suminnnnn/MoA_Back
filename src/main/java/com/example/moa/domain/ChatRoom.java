package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruit_id")
    private Optional<Recruit> recruit;

    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection
    private List<ChatMessage> messages = new ArrayList<>();
}
