package io.namoosori.webchatstudy.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // stomp 기능 활성화
// 구현을 통해 stomp 기능 설정
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    // 웹소켓 클라이언트가 어떤 경로로 서버에 접근해야 하는지 지정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chats"); // /websocket 경로로 stomp 연결
    }

    // 메시지 브로커 역할을 하기위해서 클라이언트에서 메시지를 발행하고 클라리언트는 브로커로 부터 메시지를 받기 위해 구독을 신청 해야하는데
//     그 경로를 지정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub"); // 메시지를 퍼블리시 하는 url 지정
        registry.enableSimpleBroker("/sub", "/queue"); // /sub, /queue 경로로 메시지를 구독 신청

        // 메시지 담는 코드 필요 - stomp는 컨트롤러를 사용하기에 메시지를 컨트롤러에서 받을 것임


    }
}
