package com.example.moa.controller;

import com.example.moa.domain.ChatMessage;
import com.example.moa.dto.chat.ChatMessageRequestDto;
import com.example.moa.dto.chat.ChatMessageResponseDto;
import com.example.moa.service.ChatService.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

   private final ChatService chatService;

   @GetMapping("/chat/{roomId}/messages")
   public ResponseEntity<List<ChatMessageResponseDto>> getChatMessages(@PathVariable String roomId) {
       List<ChatMessageResponseDto> chatMessages = chatService.getChatMessagesByRoomId(roomId);
       return ResponseEntity.ok(chatMessages);
   }

}


