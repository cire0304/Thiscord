package com.example.thiscode.core.commutity.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.core.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.core.commutity.controller.request.ExitDmRoomRequest;
import com.example.thiscode.core.commutity.service.RoomService;
import com.example.thiscode.core.commutity.service.dto.RoomDmInfoDto;
import com.example.thiscode.core.commutity.controller.request.ShowRoomsResponse;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoomControllerTest extends CustomControllerTestSupport {

    @MockBean
    private RoomService roomService;

    @DisplayName("대화창 목록을 조회한다.")
    @Test
    public void getRooms() throws Exception {
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();

        RoomDmInfoDto roomDmInfoA = new RoomDmInfoDto(1L, "user nickname A");
        RoomDmInfoDto roomDmInfoB = new RoomDmInfoDto(2L, "user nickname B");

        List<RoomDmInfoDto> roomDmInfoList = List.of(roomDmInfoA, roomDmInfoB);
        given(roomService.getRoomList(any())).willReturn(roomDmInfoList);

        ShowRoomsResponse response = new ShowRoomsResponse(roomDmInfoList);
        //when //then
        mockMvc.perform(get("/rooms")
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(
                        document("room-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("DM 방을 생성한다.")
    @Test
    public void createDmRoom() throws Exception {
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        CreateDmRoomRequest request = new CreateDmRoomRequest(1L);

        //when //then
        mockMvc.perform(post("/rooms/dm-room")
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("상대방과의 새로운 방을 만들었습니다."))
                .andDo(
                        document("room-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("DM 방을 나간다.")
    @Test
    public void exitDmRoom() throws Exception {
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        ExitDmRoomRequest request = new ExitDmRoomRequest(1L);

        //when //then
        mockMvc.perform(delete("/rooms/dm-room/users/me")
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("방을 나갔습니다."))
                .andDo(
                        document("room-exit",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

}
