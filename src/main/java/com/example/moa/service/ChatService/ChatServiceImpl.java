package com.example.moa.service.ChatService;

import com.example.moa.domain.ChatMessage;
import com.example.moa.domain.ChatRoom;
import com.example.moa.domain.User;
import com.example.moa.dto.chat.ChatMessageRequestDto;
import com.example.moa.dto.chat.ChatMessageResponseDto;
import com.example.moa.dto.chat.ChatRoomDto;
import com.example.moa.exception.NotFindException;
import com.example.moa.repository.ChatMessageRepository;
import com.example.moa.repository.ChatRoomRepository;
import com.example.moa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements  ChatService{
    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<ChatMessageResponseDto> getChatMessagesByRoomId(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() ->  new NotFindException(roomId + "chatRoom is not found"));

        return chatRoom.getMessages()
                .stream()
                .map(ChatMessageResponseDto :: from)
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessage saveChatMessage(ChatMessageRequestDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId())
                .orElseThrow(()-> new NotFindException(chatMessageDto.getRoomId() + "chatRoom is not found"));

        System.out.println("chatRoom find success");
        System.out.println("chatRoom name : "+chatRoom.getName());
        System.out.println("get LocalTime : " + LocalDateTime.from(LocalDate.now()).getClass().getName());

        ChatMessage chatMessage = chatMessageRepository.save(
                ChatMessage.builder()
                        .chatRoom(chatRoom)
                        .content(chatMessageDto.getContent())
                        .sender(chatMessageDto.getSender())
                        .timestamp(LocalDateTime.from(LocalDate.now()))
                        .build()
        );

        System.out.println("chatMessage : "+ chatMessage);
        return chatMessage;
    }

//    @Override
//    public String createChatRoom(){
//        String roomId = "personal-"+ChatRoom.num++;
//        chatRoomRepository.save(
//                ChatRoom.builder()
//                        .id(roomId)
//                        .build()
//        );
//        return roomId;
//    }

    @Override
    public List<ChatRoomDto> showList(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + email));

        List<String> chatRoomId = user.getChatRoomId();
        List<ChatRoomDto> chatRooms = new ArrayList<>();

        for(String id : chatRoomId){
            ChatRoom chatRoom = chatRoomRepository.findById(id)
                    .orElseThrow(()->new NotFindException(id + "chatRoom is not found"));

            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .Id(id)
                    .name(chatRoom.getName())
                    .build();

            chatRooms.add(chatRoomDto);
        }
        return chatRooms;
    }

}
