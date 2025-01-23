package be.ucll.campus_app.repositories;

import be.ucll.campus_app.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByCampus_Name(String campusName);
}
