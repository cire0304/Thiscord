package com.example.thiscode.core.commutity.controller;

import com.example.thiscode.core.commutity.controller.request.CreateDmRoomRequest;
import com.example.thiscode.core.commutity.controller.request.ExitDmRoomRequest;
import com.example.thiscode.core.commutity.service.RoomService;
import com.example.thiscode.core.commutity.controller.request.ShowRoomsResponse;
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
    public ResponseEntity<ShowRoomsResponse> rooms(@AuthenticationPrincipal JwtSubject jwtSubject) {
        ShowRoomsResponse response = new ShowRoomsResponse(roomService.getRoomList(jwtSubject.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rooms/dm-room")
    public ResponseEntity<String> createDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @RequestBody CreateDmRoomRequest request) {
        roomService.createDmRoom(jwtSubject.getId(), request.getOtherUserId());
        return ResponseEntity.ok("상대방과의 새로운 방을 만들었습니다.");
    }

    @DeleteMapping("/rooms/dm-room/users/me")
    public ResponseEntity<String> exitDmRoom(@AuthenticationPrincipal JwtSubject jwtSubject,
                                             @RequestBody ExitDmRoomRequest request) {
        roomService.exitDmRoom(jwtSubject.getId(), request.getRoomId());
        return ResponseEntity.ok("방을 나갔습니다.");
    }

}
