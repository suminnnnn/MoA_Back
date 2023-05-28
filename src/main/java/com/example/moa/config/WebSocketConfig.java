package com.example.moa.config;

import com.example.moa.controller.ChatEndpoint;
import com.example.moa.service.ChatService.ChatService;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    @Autowired
    private ChatService chatService;

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpointInstance = super.getEndpointInstance(endpointClass);
        if (endpointInstance instanceof ChatEndpoint) {
            ChatEndpoint chatEndpoint = (ChatEndpoint) endpointInstance;
            chatEndpoint.setChatService(chatService);
        }
        return endpointInstance;
    }
}

