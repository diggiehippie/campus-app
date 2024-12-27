package be.ucll.campus_app.repositories;

import be.ucll.campus_app.models.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, String> {
}
