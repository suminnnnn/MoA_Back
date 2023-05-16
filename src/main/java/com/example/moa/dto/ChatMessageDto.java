package com.example.moa.dto;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private String roomId;

    private String content;

    private String sender;
}
