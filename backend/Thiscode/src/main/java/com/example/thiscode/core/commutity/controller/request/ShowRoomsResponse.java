package com.example.thiscode.core.commutity.controller.request;

import com.example.thiscode.core.commutity.service.dto.RoomDmInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ShowRoomsResponse {

    private List<RoomDmInfoDto> roomDmInfos;

    public ShowRoomsResponse(List<RoomDmInfoDto> roomDmInfos) {
        this.roomDmInfos = roomDmInfos;
    }

}
