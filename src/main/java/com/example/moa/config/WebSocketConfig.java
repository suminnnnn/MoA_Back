package com.example.moa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커를 설정합니다.
        registry.enableSimpleBroker("/topic"); // 토픽 기반 메시지 브로커 설정
    }

    @Override

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 엔드포인트를 등록합니다.
        registry.addEndpoint("/chat").withSockJS(); // 클라이언트와의 WebSocket 연결을 담당하는 엔드포인트 설정
    }
}
