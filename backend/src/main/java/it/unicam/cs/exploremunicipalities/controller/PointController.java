package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/points")
public class PointController {
    private final PointService pointService;

    @GetMapping("/{municipalityId}")
    public ResponseEntity<Object> getPoints(@PathVariable long municipalityId) {
        try {
            return ResponseEntity.ok(this.pointService.getPoints(municipalityId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletePoint/{pointId}")
    public ResponseEntity<Object> deletePoint(@PathVariable long pointId) {
        try {
            this.pointService.deletePoint(pointId);
            return ResponseEntity.ok().body("Point deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
