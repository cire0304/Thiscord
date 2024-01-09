package com.example.thiscode.core.commutity.controller.request;

import com.example.thiscode.core.commutity.service.dto.RoomDmInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ShowRoomsResponse {

    private List<RoomDmInfo> roomDmInfos;

    public ShowRoomsResponse(List<RoomDmInfo> roomDmInfos) {
        this.roomDmInfos = roomDmInfos;
    }

}
