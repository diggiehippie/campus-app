package be.ucll.campus_app.controllers;

import be.ucll.campus_app.models.Room;
import be.ucll.campus_app.services.RoomService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<Room>> getRoomsByCampus(@PathVariable String campusName) {
        List<Room> rooms = roomService.getRoomsByCampus(campusName);
        if (rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<?> addRoomToCampus(@PathVariable String campusName, @Valid @RequestBody Room room) {
        try {
            Room createdRoom = roomService.addRoomToCampus(campusName, room);
            return ResponseEntity.status(201).body(createdRoom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        if (roomService.getRoomById(roomId).isPresent()) {
            roomService.deleteRoom(roomId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
