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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Column(name = "content")
    private String content;

    @Column(name = "sender")
    private String sender;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
