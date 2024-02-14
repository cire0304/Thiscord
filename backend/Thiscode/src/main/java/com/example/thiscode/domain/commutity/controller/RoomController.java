package com.example.thiscode.domain.commutity.controller;

import com.example.thiscode.domain.commutity.controller.request.*;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.dto.GroupRoomDTO;
import com.example.thiscode.domain.commutity.service.RoomService;
import com.example.thiscode.domain.commutity.dto.RoomUserDTO;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/api/rooms")
    public ResponseEntity<FindRoomsResponse> findRooms(@AuthenticationPrincipal JwtSubject jwtSubject) {
        FindRoomsResponse response = new FindRoomsResponse(roomService.getRoomListByUserId(jwtSubject.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/rooms/dm-room")
    public ResponseEntity<DmRoomDTO> createDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                                  @RequestBody CreateDmRoomRequest request) {
        return ResponseEntity.ok(roomService.createDmRoom(jwtSubject.getId(), request.getOtherUserId()));
    }

    @PostMapping("/api/rooms/group-room")
    public ResponseEntity<GroupRoomDTO> createGroupRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                                        @RequestBody CreateGroupRoomRequest request) {
        GroupRoomDTO groupRoom = roomService.createGroupRoom(
                jwtSubject.getId(),
                request.getGroupName(),
                request.getOtherUserIds());
        return ResponseEntity.ok(groupRoom);
    }

    @GetMapping("/api/rooms/{roomId}/users/{userId}")
    public ResponseEntity<RoomUserDTO> findRoomUser(@PathVariable("roomId") Long roomId,
                                                    @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(roomService.findRoomUser(userId, roomId));
    }

    @DeleteMapping("/api/rooms/{roomId}/users/me")
    public ResponseEntity<String> exitRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @PathVariable("roomId") Long roomId) {
        roomService.exitRoom(jwtSubject.getId(), roomId);
        return ResponseEntity.ok("방을 나갔습니다.");
    }

    @PostMapping("/api/rooms/{roomId}/users/{userId}")
    public ResponseEntity<String> inviteUser(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @PathVariable("roomId") Long roomId,
                                             @PathVariable("userId") Long userId) {
        roomService.inviteUserToRoom(jwtSubject.getId(), userId, roomId);
        return ResponseEntity.ok("유저를 초대했습니다.");
    }

}
