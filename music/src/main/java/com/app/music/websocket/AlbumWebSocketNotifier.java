package com.app.music.websocket;

import com.app.music.event.AlbumCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlbumWebSocketNotifier {

    private final SimpMessagingTemplate messagingTemplate;

    public AlbumWebSocketNotifier(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void onAlbumCreated(AlbumCreatedEvent event) {
        messagingTemplate.convertAndSend(
                "/topic/albuns",
                event.album()
        );
    }
}
