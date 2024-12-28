package be.ucll.campus_app.services;

import be.ucll.campus_app.models.Reservation;
import be.ucll.campus_app.models.Room;
import be.ucll.campus_app.models.User;
import be.ucll.campus_app.repositories.ReservationRepository;
import be.ucll.campus_app.repositories.RoomRepository;
import be.ucll.campus_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Gebruiker met ID " + userId + " niet gevonden");
        }
        return reservationRepository.findByUser_Id(userId);
    }

    public Reservation addReservation(Long userId, Reservation reservation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker met ID " + userId + " niet gevonden"));

        if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
            throw new IllegalArgumentException("De begintijd moet voor de eindtijd liggen.");
        }

        if (reservation.getEndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("De reservatie kan niet in het verleden liggen.");
        }

        int totalCapacity = reservation.getRooms().stream()
                        .mapToInt(Room::getCapacity)
                        .sum();

        if (reservation.getUser().getReservations().size() > totalCapacity) {
            throw new IllegalArgumentException("Het aantal personen overschrijdt de totale capaciteit van de geselecteerde kamers.");
        }

        reservation.setUser(user);

        for (Room room : reservation.getRooms()) {
            boolean isRoomOverlapping = reservationRepository.findByRooms_IdAndStartTimeLessThanAndEndTimeGreaterThan(
                    room.getId(),
                    reservation.getEndTime(),
                    reservation.getStartTime()
            ).size() > 0;

            if (isRoomOverlapping) {
                throw new IllegalArgumentException("Room '" + room.getName() + "' is already reserved during this time.");
            }
        }

        return reservationRepository.save(reservation);
    }

    // Verwijder een reservatie
    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("Reservation not found with ID: " + reservationId);
        }
        reservationRepository.deleteById(reservationId);
    }
}
