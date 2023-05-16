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
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@RequestBody ChatMessageRequestDto chatMessageDto) {
        // 채팅 메시지를 저장
        ChatMessage chatMessage = chatService.saveChatMessage(chatMessageDto);

        // 메시지를 해당 채팅방의 구독자에게 전송
        messagingTemplate.convertAndSend("/topic/room/" + chatMessage.getRoomId(), chatMessage);
    }

    @SubscribeMapping("/chat/{roomId}")
    public void subscribeToRoom(@DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor) {
        String topic = "/topic/room/" + roomId;
        String sessionId = headerAccessor.getSessionId();
        messagingTemplate.convertAndSend(topic, "New user subscribed: " + sessionId);
    }

    @GetMapping("/chat/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponseDto>> getChatMessages(@PathVariable String roomId) {
        List<ChatMessageResponseDto> chatMessages = chatService.getChatMessagesByRoomId(roomId);
        return ResponseEntity.ok(chatMessages);
    }
}


