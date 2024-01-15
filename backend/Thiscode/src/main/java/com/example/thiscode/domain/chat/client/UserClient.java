package com.example.thiscode.domain.chat.client;

import com.example.thiscode.domain.chat.client.dto.UserInfo;
import com.example.thiscode.domain.chat.client.dto.UserInfosResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserClient {

    @Value("${user.uri}")
    private String BASE_URL = "http://localhost:8080";
    @Value("${user.cookie.jwt}")
    private String JWT_COOKIE_VALUE;
    private String REQUEST_URI = "/users";
    private String TOKEN = "TOKEN";

    private WebClient webClient;


    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultCookie(TOKEN, JWT_COOKIE_VALUE)
                .build();
    }

    public Map<Long, UserInfo> getUserMap(List<Long> userIds) {
        UserInfosResponse response = webClient.post()
                .uri(REQUEST_URI)
                .bodyValue(userIds)
                .retrieve()
                .bodyToMono(UserInfosResponse.class)
                .block();
        Map<Long, UserInfo> userInfoMap = new HashMap<>();
        response.getUserInfos().forEach(userInfo -> userInfoMap.put(userInfo.getId(), userInfo));
        return userInfoMap;
    }

}
