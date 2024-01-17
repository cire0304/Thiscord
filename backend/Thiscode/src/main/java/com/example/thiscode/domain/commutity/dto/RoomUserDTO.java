package com.example.thiscode.domain.commutity.dto;

import com.example.thiscode.domain.commutity.entity.type.RoomUserState;
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
