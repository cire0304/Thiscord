package com.example.thiscode.domain.commutity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomListDTO {

    List<DmRoomDTO> dmRooms;
    List<GroupRoomDTO> groupRooms;

}
