package io.namoosori.webchatstudy.configs;

import io.namoosori.webchatstudy.handlers.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor // 생성자 자동으로 만들어줌
@Configuration// 서버에서 사용할 수 있게 등록하는 역할
@EnableWebSocket // 우리 서버가 웹소켓을 사용할 것이라는 표시

// 웹소켓 설정을 위해 WebSocketConfigurer 구현
public class WebsockectConfig implements WebSocketConfigurer {

    final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandler, "/ws/chats"); // 웹소켓 핸들러 등록 , 어떠한 경로로 서버에 접근했을 때 이 핸들러 적용할 것 인지
    }
}
