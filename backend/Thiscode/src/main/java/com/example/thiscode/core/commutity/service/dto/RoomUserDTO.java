package com.example.thiscode.core.commutity.service.dto;

import com.example.thiscode.core.commutity.entity.type.RoomUserState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomUserDTO {

    private Long userId;
    private String email;
    private String nickname;
    private String userCode;
    private String introduction;
    private RoomUserState state;

}
