package com.example.thiscode.core.user.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FriendInfoDto {

    private Long id;
    private Long userId;
    private String email;
    private String nickname;
    private String userCode;

}
