package org.pah_monitoring.main.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload Notification notification) {
        System.out.println("Recipient id: " + notification.getRecipientId().toString());
        messagingTemplate.convertAndSendToUser(notification.getRecipientId().toString(), "/queue/messages", notification);
    }

    @Data
    public static class Notification {
        private Integer messageId;
        private Integer recipientId;
        private String messageState;
    }

}
