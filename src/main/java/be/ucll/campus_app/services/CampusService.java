package be.ucll.campus_app.services;

import be.ucll.campus_app.models.Campus;
import be.ucll.campus_app.repositories.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampusService {
    @Autowired
    private CampusRepository campusRepository;

    public List<Campus> getAllCampuses() {
        return campusRepository.findAll();
    }

    public Optional<Campus> getCampusByName(String name) {
        return campusRepository.findById(name);
    }

    public Campus addCampus(Campus campus) {
        if (campusRepository.existsById(campus.getName())) {
            throw new IllegalArgumentException("Campus met naam '" + campus.getName() + "' bestaat al.");
        }
        return campusRepository.save(campus);
    }

    public void deleteCampus(String name) {
        if (!campusRepository.existsById(name)) {
            throw new IllegalArgumentException("Campus met naam '" + name + "' bestaat niet.");
        }
        campusRepository.deleteById(name);
    }
}
