package com.example.thiscode.user.controller.response;

import com.example.thiscode.user.service.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: Delete this class. Because Wrapper class is not needed for response.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfosResponse {

    List<UserDTO> userInfos;

}
