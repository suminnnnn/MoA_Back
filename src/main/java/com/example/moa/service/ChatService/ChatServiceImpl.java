package com.example.moa.service.ChatService;

import com.example.moa.domain.ChatMessage;
import com.example.moa.dto.ChatMessageDto;
import com.example.moa.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements  ChatService{
    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }

    public ChatMessage saveChatMessage(ChatMessageDto chatMessageDto) {
        return chatMessageRepository.save(
                ChatMessage.builder()
                        .roomId(chatMessageDto.getRoomId())
                        .content(chatMessageDto.getContent())
                        .sender(chatMessageDto.getSender())
                        .timestamp(LocalDateTime.from(LocalDate.now()))
                        .build()
        );
    }

}
