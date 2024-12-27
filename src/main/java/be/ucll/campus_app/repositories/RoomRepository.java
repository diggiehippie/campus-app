package be.ucll.campus_app.repositories;

import be.ucll.campus_app.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
