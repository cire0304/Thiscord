package com.example.thiscode.core.user.entity.type;

public enum State {
    REQUEST("요청중"),
    ACCEPT("수락됨"),
    REJECT("거절됨");

    private String description;

    State(String description) {
        this.description = description;
    }
}
