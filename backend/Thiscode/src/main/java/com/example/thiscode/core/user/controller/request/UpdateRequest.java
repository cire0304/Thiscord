package com.example.thiscode.core.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
    @NotBlank(message = "자기소개는 필수입니다.")
    private String introduction;

}
