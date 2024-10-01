package io.namoosori.webchatstudy.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StompController {

    // payload: 전송되어야 하는 데이터 자체(바디)
    @MessageMapping("/chats") // 어떤 경로로 전달된 메시지를 다룰 것인지 표시 -> 클라리언트가 /pub/chat 으로 메시지 발행시 이곳으로 오게됨. /pub 생략 O
    @SendTo("/sub/chats") // 리턴된 메시지는 "/sub/chats"을 구독한 클라이언트에게 전달함
    public String handleMessage(@Payload String message) {
        log.info("{} recived :" , message);

        return message;
    }

}
