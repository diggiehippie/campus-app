package be.ucll.campus_app.services;

import be.ucll.campus_app.models.Campus;
import be.ucll.campus_app.models.Room;
import be.ucll.campus_app.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CampusService campusService;

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByCampus(String campusName) {
        Optional<Campus> campus = campusService.getCampusByName(campusName);
        return campus.map(Campus::getRooms).orElseThrow(() -> new IllegalArgumentException("Campus niet gevonden."));
    }

    public Room addRoomToCampus(String campusName, Room room) {
        Campus campus = campusService.getCampusByName(campusName)
                .orElseThrow(() -> new IllegalArgumentException("Campus niet gevonden."));
        room.setCampus(campus);
        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }
}
