package com.example.thiscode.domain.commutity.event;

import lombok.Getter;

@Getter
public enum RoomEventType {

    JOIN_ROOM("JOIN_ROOM"),
    EXIT_ROOM("EXIT_ROOM");

    private String value;

    RoomEventType(String value) {
        this.value = value;
    }

}
