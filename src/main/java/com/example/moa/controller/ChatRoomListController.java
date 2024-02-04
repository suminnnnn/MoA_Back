package com.example.moa.controller;

import com.example.moa.dto.chat.ChatRoomDto;
import com.example.moa.service.ChatService.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ChatRoomListController {

    private final ChatService chatService;

    @GetMapping("/chat/list")
    public ResponseEntity<List<ChatRoomDto>> showChatRoomList(HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        List<ChatRoomDto> chatRooms = chatService.showList(email);

        return ResponseEntity.ok().body(chatRooms);
    }
}
