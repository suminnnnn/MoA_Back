package com.example.moa.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto {

    private String roomId;

    private String content;

    private String sender;
}
