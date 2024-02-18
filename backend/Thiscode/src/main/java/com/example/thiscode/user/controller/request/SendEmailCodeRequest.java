package com.example.thiscode.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailCodeRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

}
