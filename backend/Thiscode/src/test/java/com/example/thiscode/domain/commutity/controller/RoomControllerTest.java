package com.example.thiscode.domain.commutity.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.domain.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.domain.commutity.entity.type.RoomUserState;
import com.example.thiscode.domain.commutity.service.RoomService;
import com.example.thiscode.domain.commutity.service.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.controller.request.FindRoomsResponse;
import com.example.thiscode.domain.commutity.service.dto.RoomUserDTO;
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
        Long roomAId = 1L;
        Long roomBId = 2L;
        Long RoomUserAId = 1L;
        Long RoomUserBId = 2L;


        DmRoomDTO roomDmInfoA = new DmRoomDTO(roomAId, RoomUserAId, "user nickname A");
        DmRoomDTO roomDmInfoB = new DmRoomDTO(roomBId, RoomUserBId, "user nickname B");

        List<DmRoomDTO> roomDmInfoList = List.of(roomDmInfoA, roomDmInfoB);
        given(roomService.getRoomList(any())).willReturn(roomDmInfoList);

        FindRoomsResponse response = new FindRoomsResponse(roomDmInfoList);
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

    @DisplayName("DM 방에 참여한 사용자의 정보를 가져온다.")
    @Test
    public void getRoomUser() throws Exception {
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        RoomUserDTO roomUserDTO = RoomUserDTO.builder()
                .userId(20L)
                .nickname("userNickname")
                .email("userEmail")
                .userCode("userCode")
                .introduction("userIntroduction")
                .state(RoomUserState.JOIN)
                .build();
        given(roomService.findRoomUser(any(), any(), any())).willReturn(roomUserDTO);

        //when //then
        mockMvc.perform(get("/rooms/dm-room/{roomId}/users/{userId}", 1, 20)
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(roomUserDTO)))
                .andDo(
                        document("room-user-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("DM 방을 나간다.")
    @Test
    public void exitDmRoom() throws Exception {

        StringBuilder sb = new StringBuilder();

        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        long roomId = 1L;

        //when //then
        mockMvc.perform(delete("/rooms/dm-room/{roomId}/users/me", roomId)
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
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
