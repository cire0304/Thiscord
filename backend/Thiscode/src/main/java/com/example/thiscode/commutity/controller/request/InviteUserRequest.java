package com.example.thiscode.commutity.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteUserRequest {

    @NotNull(message = "초대할 방의 식별자를 입력해주세요.")
    private Long roomId;

    @NotNull(message = "초대할 유저의 아이디를 입력해주세요.")
    private Long userId;

}
