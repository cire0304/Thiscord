package com.example.thiscode.domain.user.controller.request;

import com.example.thiscode.domain.user.entity.type.State;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFriendStateRequest {

    @NotBlank(message = "친구 요청 식별자를 입력해주세요.")
    private Long id;

    @NotBlank(message = "친구 요청 상태를 입력해주세요.")
    private State state;

}
