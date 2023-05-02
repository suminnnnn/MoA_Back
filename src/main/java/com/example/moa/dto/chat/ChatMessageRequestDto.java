//package com.example.moa.dto.chat;
//
//import com.example.moa.domain.ChatMessage;
//import com.example.moa.domain.ChatRoom;
//import lombok.*;
//
//import java.time.LocalDate;
//@Builder
//@Getter
//@Setter
//@RequiredArgsConstructor
//@AllArgsConstructor
//public class ChatMessageRequestDto {
//    private String sender;
//    private String message;
//
//    private LocalDate timestamp;
//
//    private ChatRoom chatRoom;
//
//    public ChatMessage toEntity(){
//       return  ChatMessage.builder()
//                .sender(sender)
//                .message(message)
//                .timestamp(LocalDate.now())
//                .chatRoom(chatRoom)
//                .build();
//    }
//}
