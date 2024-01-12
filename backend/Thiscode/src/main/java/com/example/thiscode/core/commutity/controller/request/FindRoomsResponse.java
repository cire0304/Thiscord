package com.example.thiscode.core.commutity.controller.request;

import com.example.thiscode.core.commutity.service.dto.DmRoomDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindRoomsResponse {

    // TODO: I'm going to append GroupRoomDTO class here not just DmRoomDTO
    private List<DmRoomDTO> rooms;

    public FindRoomsResponse(List<DmRoomDTO> rooms) {
        this.rooms = rooms;
    }

}
