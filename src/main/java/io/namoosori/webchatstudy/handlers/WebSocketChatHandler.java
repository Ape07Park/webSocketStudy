package io.namoosori.webchatstudy.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 원래라면 WebsocketHandler를 구현해야 하지만(작성해야할 코드가 많아짐) 스프링에서 제공하는 TextWebSocketHandler를 상속받아서 사용하면 필요한 거만 구현하면 됨 더 편함
@Slf4j
@Component // 스프링의 빈으로 등록
public class WebSocketChatHandler extends TextWebSocketHandler {

    // 웹소켓에 접속한 클라이언트 정보 가지고 있음. 이것으로 메시지 전송 O
    final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    // 웹 소켓 클라이언트가 서버로 연결한 이후 실행되는 코드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} connected", session.getId());
        // 세션이 유저가 접속한 상태를 나타내므로 id와 세션값을 이용해 유저를 구분
        this.webSocketSessionMap.put(session.getId(), session);
    }

    // 웹소켓 클라이언트에서 메시지가 왔을 때 사용하는 코드

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 어떤 아이디가 어떤 메시지를 보냄
        log.info("{} sent {}", session.getId(), message.getPayload());

        // 메시지를 다른 웹소켓 클라이언트에게 전달해 주는 로직
        this.webSocketSessionMap.values().forEach(
                webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    // 웹소켓 클라이언트가 연결을 끊었을 때 사용하는 코드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} disconnected", session.getId());

        this.webSocketSessionMap.remove(session.getId()); // 세션에서 제거
    }
}
