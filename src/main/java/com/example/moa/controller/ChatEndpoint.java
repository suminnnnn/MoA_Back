package com.example.moa.controller;


import com.example.moa.domain.ChatMessage;
import com.example.moa.dto.chat.ChatMessageRequestDto;
import com.example.moa.service.ChatService.ChatService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;



@AllArgsConstructor
@NoArgsConstructor
@ServerEndpoint("/chat/{roomId}/{userEmail}")
@Component
public class ChatEndpoint {
    private static Map<String, Set<Session>> roomSessionMap = new HashMap<>();

    private ChatService chatService;


    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        System.out.println("roomId : "+roomId);
        Set<Session> roomSessions = roomSessionMap.computeIfAbsent(roomId, key -> Collections.synchronizedSet(new HashSet<>()));
        roomSessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("roomId") String roomId, @PathParam("userEmail") String userEmail, String message) throws IOException{
        ChatMessage chatMessage = chatService.saveChatMessage(
                ChatMessageRequestDto.builder()
                        .roomId(roomId)
                        .sender(userEmail)
                        .content(message)
                        .build()
        );
        broadcast(chatMessage.getContent(), roomId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("roomId") String roomId) {
        Set<Session> sessions = roomSessionMap.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    @OnError
    public void onError(Session session, @PathParam("roomId") String roomId, Throwable throwable) {
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

//    private String extractRoomIdFromPath(String path) {
//        String[] parts = path.split("/");
//        return parts[3];
//    }
}

