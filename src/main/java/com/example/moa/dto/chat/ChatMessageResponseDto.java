package com.example.moa.dto.chat;

import com.example.moa.domain.ChatMessage;
import com.example.moa.domain.ChatRoom;
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
        ChatRoom chatRoom = chatMessage.getChatRoom();
        return ChatMessageResponseDto.builder()
                .id(chatMessage.getId())
                .roomId(chatRoom.getId())
                .content(chatMessage.getContent())
                .sender(chatMessage.getSender())
                .timestamp(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .format(chatMessage.getTimestamp()))
                .build();
    }
}
