package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy="chatRoom", cascade = CascadeType.ALL)
    @OrderBy("timestamp ASC")
    private List<ChatMessage> messages = new ArrayList<>();

    public static Long num = 1L;
}
