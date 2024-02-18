package com.example.thiscode.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
    @NotBlank(message = "패스워드는 필수입니다.")
    private String password;
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

}
