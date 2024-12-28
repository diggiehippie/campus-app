package be.ucll.campus_app.repositories;

import be.ucll.campus_app.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRooms_IdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long roomId, LocalDateTime endTime, LocalDateTime startTime
    );

    List<Reservation> findByUser_Id(Long userId);
}
