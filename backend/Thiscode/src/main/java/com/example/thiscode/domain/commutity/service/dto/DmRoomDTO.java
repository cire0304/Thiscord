package com.example.thiscode.domain.commutity.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DmRoomDTO {
    private Long roomId;
    private Long otherUserId;
    private String otherUserNickname;
}
