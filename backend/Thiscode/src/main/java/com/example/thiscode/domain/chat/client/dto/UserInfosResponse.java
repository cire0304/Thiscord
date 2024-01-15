package com.example.thiscode.domain.chat.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfosResponse {

    List<UserInfo> userInfos;

}
