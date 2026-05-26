//package com.example.bankingApp.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Component
//@Slf4j
//public class WebSocketEventListener {
//
//    @EventListener
//    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
//        log.info("🔌 WebSocket disconnected: " + event.getSessionId());
//    }
//}
