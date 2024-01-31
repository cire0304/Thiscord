package com.example.thiscode.domain.commutity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRoomDTO {

    private Long roomId;
    private String groupName;
    private List<RoomUserDTO> roomUsers;

}
