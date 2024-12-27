package be.ucll.campus_app.controllers;

import be.ucll.campus_app.models.Room;
import be.ucll.campus_app.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campus/{campusName}/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getRoomsByCampus(@PathVariable String campusName) {
        return roomService.getRoomsByCampus(campusName);
    }

    @PostMapping
    public ResponseEntity<Room> addRoomToCampus(@PathVariable String campusName, @RequestBody Room room) {
        Room createdRoom = roomService.addRoomToCampus(campusName, room);
        return ResponseEntity.ok(createdRoom);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
