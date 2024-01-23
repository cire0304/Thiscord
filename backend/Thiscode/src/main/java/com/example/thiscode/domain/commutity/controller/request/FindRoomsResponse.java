package com.example.thiscode.domain.commutity.controller.request;

import com.example.thiscode.domain.commutity.dto.RoomListDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindRoomsResponse {

    // TODO: I'm going to append GroupRoomDTO class here not just DmRoomDTO
    private RoomListDTO rooms;

    public FindRoomsResponse(RoomListDTO rooms) {
        this.rooms = rooms;
    }

}
