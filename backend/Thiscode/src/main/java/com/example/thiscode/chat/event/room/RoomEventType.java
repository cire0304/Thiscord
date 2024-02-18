package com.example.thiscode.chat.event.room;

import lombok.Getter;

@Getter
public enum RoomEventType {

    JOIN_ROOM("JOIN_ROOM"),
    EXIT_ROOM("EXIT_ROOM"),
    INVITE_ROOM("INVITE_ROOM");

    private String value;

    RoomEventType(String value) {
        this.value = value;
    }

}
