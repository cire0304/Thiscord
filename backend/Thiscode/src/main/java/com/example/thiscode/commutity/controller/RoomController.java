package com.example.thiscode.commutity.controller;

import com.example.thiscode.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.commutity.controller.request.CreateGroupRoomRequest;
import com.example.thiscode.commutity.controller.request.FindRoomsResponse;
import com.example.thiscode.commutity.dto.DmRoomDTO;
import com.example.thiscode.commutity.dto.RoomUserDTO;
import com.example.thiscode.commutity.dto.GroupRoomDTO;
import com.example.thiscode.commutity.service.RoomService;
import com.example.thiscode.security.jwt.JwtSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<FindRoomsResponse> findRooms(@AuthenticationPrincipal JwtSubject jwtSubject) {
        FindRoomsResponse response = new FindRoomsResponse(roomService.getRoomListByUserId(jwtSubject.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dm-room")
    public ResponseEntity<DmRoomDTO> createDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                                  @RequestBody CreateDmRoomRequest request) {
        return ResponseEntity.ok(roomService.createDmRoom(jwtSubject.getId(), request.getOtherUserId()));
    }

    @PostMapping("/group-room")
    public ResponseEntity<GroupRoomDTO> createGroupRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                                        @RequestBody CreateGroupRoomRequest request) {
        GroupRoomDTO groupRoom = roomService.createGroupRoom(
                jwtSubject.getId(),
                request.getGroupName(),
                request.getOtherUserIds());
        return ResponseEntity.ok(groupRoom);
    }

    @GetMapping("/{roomId}/users/{userId}")
    public ResponseEntity<RoomUserDTO> findRoomUser(@PathVariable("roomId") Long roomId,
                                                    @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(roomService.findRoomUser(userId, roomId));
    }

    @DeleteMapping("/{roomId}/users/me")
    public ResponseEntity<String> exitRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @PathVariable("roomId") Long roomId) {
        roomService.exitRoom(jwtSubject.getId(), roomId);
        return ResponseEntity.ok("방을 나갔습니다.");
    }

    @PostMapping("/{roomId}/users/{userId}")
    public ResponseEntity<String> inviteUser(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @PathVariable("roomId") Long roomId,
                                             @PathVariable("userId") Long userId) {
        roomService.inviteUserToRoom(jwtSubject.getId(), userId, roomId);
        return ResponseEntity.ok("유저를 초대했습니다.");
    }

}
