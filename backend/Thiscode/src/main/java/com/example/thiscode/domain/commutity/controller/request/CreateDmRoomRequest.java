package com.example.thiscode.domain.commutity.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDmRoomRequest {

    @NotBlank(message = "상대방의 아이디를 입력해주세요.")
    private Long otherUserId;

}
