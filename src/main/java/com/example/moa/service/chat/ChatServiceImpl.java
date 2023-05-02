package com.example.moa.service.chat;

import com.example.moa.domain.ChatMessage;
import com.example.moa.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessage(Long id){
        return chatMessageRepository.findAllByOrderByTimestampAsc();
    }
}
