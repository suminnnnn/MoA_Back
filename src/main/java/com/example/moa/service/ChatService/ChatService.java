package com.example.moa.service.ChatService;

import com.example.moa.domain.ChatMessage;
import com.example.moa.dto.ChatMessageDto;

import java.util.List;

public interface ChatService {
    ChatMessage saveChatMessage(ChatMessageDto chatMessageDto);
    List<ChatMessage> getChatMessagesByRoomId(String roomId);
}
