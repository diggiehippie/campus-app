package be.ucll.campus_app.controllers;

import be.ucll.campus_app.models.Reservation;
import be.ucll.campus_app.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<?> addReservation(
            @PathVariable Long userId,
            @Valid @RequestBody Reservation reservation
    ) {
        if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
            return ResponseEntity.badRequest().body("Start time must be before end time.");
        }

        Reservation createdReservation = reservationService.addReservation(userId, reservation);
        return ResponseEntity.status(201).body(createdReservation);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        if (reservationService.getReservationById(reservationId).isPresent()) {
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
