package com.example.thiscode.domain.commutity.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.domain.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.domain.commutity.dto.GroupRoomDTO;
import com.example.thiscode.domain.commutity.dto.RoomListDTO;
import com.example.thiscode.domain.commutity.entity.type.RoomUserState;
import com.example.thiscode.domain.commutity.service.RoomService;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.controller.request.FindRoomsResponse;
import com.example.thiscode.domain.commutity.dto.RoomUserDTO;
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

        RoomUserDTO roomUserDTOA = RoomUserDTO.builder()
                .userId(RoomUserAId)
                .userCode("123456")
                .email("test@mail.com")
                .nickname("user nickname A")
                .introduction("user introduction A")
                .state(RoomUserState.JOIN)
                .build();
        RoomUserDTO roomUserDTOB = RoomUserDTO.builder()
                .userId(RoomUserBId)
                .userCode("654321")
                .email("test@mail.com")
                .nickname("user nickname B")
                .introduction("user introduction B")
                .state(RoomUserState.JOIN)
                .build();

        List<DmRoomDTO> dmRoomDTOS = List.of(new DmRoomDTO(roomAId, roomUserDTOA));
        List<GroupRoomDTO> groupRoomDTOS = List.of(new GroupRoomDTO(roomBId, "Group name", List.of(roomUserDTOA, roomUserDTOB)));
        RoomListDTO roomListDTO = new RoomListDTO(dmRoomDTOS, groupRoomDTOS);

        given(roomService.getRoomListByUserId(any())).willReturn(roomListDTO);

        FindRoomsResponse response = new FindRoomsResponse(roomListDTO);
        //when //then
        mockMvc.perform(get("/api/rooms")
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(
                        document("room-list-get",
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

        RoomUserDTO roomUserDTO = RoomUserDTO.builder()
                .userId(20L)
                .nickname("userNickname")
                .email("userEmail")
                .userCode("userCode")
                .introduction("userIntroduction")
                .state(RoomUserState.JOIN)
                .build();
        DmRoomDTO dmRoomDTO = new DmRoomDTO(1L, roomUserDTO);
        given(roomService.createDmRoom(any(), any())).willReturn(dmRoomDTO);

        //when //then
        mockMvc.perform(post("/api/rooms/dm-room")
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dmRoomDTO)))
                .andDo(
                        document("room-dm-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("Group 방을 생성한다.")
    @Test
    public void createGroupRoom() throws Exception {
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        CreateDmRoomRequest request = new CreateDmRoomRequest(1L);
        RoomUserDTO roomUserDTOA = RoomUserDTO.builder()
                .userId(20L)
                .nickname("userNickname A")
                .email("userEmail")
                .userCode("userCode")
                .introduction("userIntroduction")
                .state(RoomUserState.JOIN)
                .build();

        RoomUserDTO roomUserDTOB = RoomUserDTO.builder()
                .userId(20L)
                .nickname("userNickname B")
                .email("userEmail")
                .userCode("userCode")
                .introduction("userIntroduction")
                .state(RoomUserState.JOIN)
                .build();
        GroupRoomDTO groupRoomDTO = new GroupRoomDTO(1L, "Group name", List.of(roomUserDTOA, roomUserDTOB));
        given(roomService.createGroupRoom(any(), any(), any())).willReturn(groupRoomDTO);

        //when //then
        mockMvc.perform(post("/api/rooms/group-room")
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(groupRoomDTO)))
                .andDo(
                        document("room-group-create",
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
        given(roomService.findRoomUser(any(), any())).willReturn(roomUserDTO);

        //when //then
        mockMvc.perform(get("/api/rooms/{roomId}/users/{userId}", 1, 20)
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
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        long roomId = 1L;

        //when //then
        mockMvc.perform(delete("/api/rooms/{roomId}/users/me", roomId)
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

    @DisplayName("유저를 방에 초대한다.")
    @Test
    public void inviteUserToRoom () throws Exception {
        //given
        Cookie defaultJwtCookie = getDefaultJwtCookie();
        Long roomId = 1L;
        Long userId = 1L;

        //when
        mockMvc.perform(post("/api/rooms/{roomId}/users/{userId}", roomId, userId)
                        .contentType("application/json")
                        .cookie(defaultJwtCookie)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("유저를 초대했습니다."))
                .andDo(
                        document("room-invite",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
     }

}
