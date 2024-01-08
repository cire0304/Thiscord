package com.example.thiscode.core.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendRequest {

    @NotBlank(message = "친구 이름을 입력해주세요.")
    private String nickname;
    @NotBlank(message = "친구 코드를 입력해주세요.")
    private String userCode;

}
