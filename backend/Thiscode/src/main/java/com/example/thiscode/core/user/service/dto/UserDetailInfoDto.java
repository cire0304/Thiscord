package com.example.thiscode.core.user.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDetailInfoDto {

    private Long id;
    private String email;
    private String nickname;
    private String userCode;
    private String introduction;
    private LocalDateTime createdAt;

}
