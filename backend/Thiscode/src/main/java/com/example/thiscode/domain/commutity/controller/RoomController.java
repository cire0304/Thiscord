package com.example.thiscode.domain.commutity.controller;

import com.example.thiscode.domain.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.domain.commutity.controller.request.CreateGroupRoomRequest;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.service.RoomService;
import com.example.thiscode.domain.commutity.controller.request.FindRoomsResponse;
import com.example.thiscode.domain.commutity.dto.RoomUserDTO;
import com.example.thiscode.security.jwt.JwtSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<FindRoomsResponse> findRooms(@AuthenticationPrincipal JwtSubject jwtSubject) {
        FindRoomsResponse response = new FindRoomsResponse(roomService.getRoomListByUserId(jwtSubject.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rooms/dm-room")
    public ResponseEntity<DmRoomDTO> createDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @RequestBody CreateDmRoomRequest request) {
        return ResponseEntity.ok(roomService.createDmRoom(jwtSubject.getId(), request.getOtherUserId()));
    }

    @PostMapping("/rooms/group-room")
    public ResponseEntity<DmRoomDTO> createGroupRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                                     @RequestBody CreateGroupRoomRequest request) {
        roomService.createGroupRoom(
                jwtSubject.getId(),
                request.getGroupName(),
                request.getOtherUserIds());
        return ResponseEntity.ok(null);
    }

    @GetMapping("/rooms/dm-room/{roomId}/users/{userId}")
    public ResponseEntity<RoomUserDTO> findRoomUser(@AuthenticationPrincipal JwtSubject jwtSubject,
                                                    @PathVariable Long roomId,
                                                    @PathVariable Long userId) {
        return ResponseEntity.ok(roomService.findRoomUser(jwtSubject.getId(), roomId, userId));
    }

    @DeleteMapping("/rooms/dm-room/{roomId}/users/me")
    public ResponseEntity<String> exitDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @PathVariable Long roomId) {
        roomService.exitDmRoom(jwtSubject.getId(), roomId);
        return ResponseEntity.ok("방을 나갔습니다.");
    }

}
