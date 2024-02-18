package com.example.thiscode.commutity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DmRoomDTO {

    private Long roomId;
    private RoomUserDTO otherUser;

}
