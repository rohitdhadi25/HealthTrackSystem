package com.htps.websocket;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("Received message: " + receivedMessage);

        // Sending a response back to the client
        session.sendMessage(new TextMessage("Message received: " + receivedMessage));
    }
}
