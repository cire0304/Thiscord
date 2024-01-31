package com.example.thiscode.domain.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailCodeRequest {

    @NotBlank(message = "이메일 코드는 필수입니다.")
    private String emailCode;

}
