package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.controller.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
public class PointController {
    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("{municipalityId}")
    public ResponseEntity<Object> getPoints(@PathVariable long municipalityId) {
        try {
            return ResponseEntity.ok(this.pointService.getPoints(municipalityId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
