package com.example.moa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "content")
    private String content;

    @Column(name = "sender")
    private String sender;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
