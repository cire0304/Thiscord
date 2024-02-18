package com.example.thiscode.user.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FriendDTO {

    private Long id;
    private Long userId;
    private String email;
    private String nickname;
    private String userCode;

}
