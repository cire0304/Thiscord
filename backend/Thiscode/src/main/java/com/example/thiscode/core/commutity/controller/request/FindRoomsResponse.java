package com.example.thiscode.core.commutity.controller.request;

import com.example.thiscode.core.commutity.service.dto.DmRoomDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindRoomsResponse {

    private List<DmRoomDTO> roomDmInfos;

    public FindRoomsResponse(List<DmRoomDTO> roomDmInfos) {
        this.roomDmInfos = roomDmInfos;
    }

}
