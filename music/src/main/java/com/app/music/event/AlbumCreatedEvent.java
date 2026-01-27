package com.app.music.event;

import com.app.music.dto.AlbumResponse;

public record AlbumCreatedEvent(AlbumResponse album) {
}
