package com.example.thiscode.commutity.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRoomRequest {

    @NotBlank(message = "그룹의 이름을 입력해주세요.")
    private String groupName;

    @NotBlank(message = "상대방의 아이디를 입력해주세요.")
    @Size(min = 1, max = 9, message = "상대방의 아이디를 1이상 9개 이하로 입력해주세요.")
    private List<Long> otherUserIds;

}
