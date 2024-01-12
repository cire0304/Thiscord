package com.example.thiscode.domain.commutity.controller;

import com.example.thiscode.domain.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.domain.commutity.service.RoomService;
import com.example.thiscode.domain.commutity.controller.request.FindRoomsResponse;
import com.example.thiscode.domain.commutity.service.dto.RoomUserDTO;
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
        FindRoomsResponse response = new FindRoomsResponse(roomService.getRoomList(jwtSubject.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rooms/dm-room")
    public ResponseEntity<String> createDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @RequestBody CreateDmRoomRequest request) {
        roomService.createDmRoom(jwtSubject.getId(), request.getOtherUserId());
        return ResponseEntity.ok("상대방과의 새로운 방을 만들었습니다.");
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
