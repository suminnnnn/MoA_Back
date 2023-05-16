package com.example.moa.dto.chat;

import com.example.moa.domain.ChatMessage;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ChatMessageResponseDto {

    private Long id;

    private String roomId;

    private String content;

    private String sender;

    private String timestamp;

    public static ChatMessageResponseDto from(ChatMessage chatMessage){
        return ChatMessageResponseDto.builder()
                .id(chatMessage.getId())
                .roomId(chatMessage.getRoomId())
                .content(chatMessage.getContent())
                .sender(chatMessage.getSender())
                .timestamp(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .format(chatMessage.getTimestamp()))
                .build();
    }
}
