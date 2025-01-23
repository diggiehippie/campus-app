package be.ucll.campus_app.controllers;

import be.ucll.campus_app.models.Campus;
import be.ucll.campus_app.services.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {
    @Autowired
    private CampusService campusService;

    @GetMapping
    public List<Campus> getAllCampuses() {
        return campusService.getAllCampuses();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Campus> getCampusByName(@PathVariable String name) {
        return campusService.getCampusByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Campus> addCampus(@RequestBody Campus campus) {
        Campus createdCampus = campusService.addCampus(campus);
        return ResponseEntity.status(201).body(createdCampus);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCampus(@PathVariable String name) {
        return campusService.getCampusByName(name)
                .map(existingCampus -> {
                    campusService.deleteCampus(name);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
