package com.example.moa.controller;


import com.example.moa.dto.chat.ChatMessageRequestDto;
import com.example.moa.service.ChatService.ChatService;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@ServerEndpoint("/chat/room/{roomId}")
public class ChatEndpoint {
    private static Map<String, Set<Session>> roomSessionMap = new HashMap<>();

    @Autowired
    private final ChatService chatService;

    @OnOpen
    public void onOpen(Session session, @PathVariable String roomId) {
        Set<Session> roomSessions = roomSessionMap.computeIfAbsent(roomId, key -> Collections.synchronizedSet(new HashSet<>()));
        roomSessions.add(session);
    }

    @OnMessage
    public void onMessage(@RequestBody ChatMessageRequestDto chatMessageRequestDto, Session session, @PathVariable String roomId) throws IOException{
        chatService.saveChatMessage(chatMessageRequestDto);
        broadcast(chatMessageRequestDto.getContent(), roomId);
    }

    @OnClose
    public void onClose(Session session, @PathVariable String roomId) {
        Set<Session> sessions = roomSessionMap.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    @OnError
    public void onError(Session session, @PathVariable String roomId, Throwable throwable) {
        // 에러 처리 로직 작성
    }

    private static void broadcast(String message, String roomId) throws IOException{
        Set<Session> sessions = roomSessionMap.get(roomId);
        if (sessions != null) {
            for (Session session : sessions) {
                session.getBasicRemote().sendText(message);
            }
        }
    }
}

