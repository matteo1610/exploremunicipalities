package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.controller.dto.request.CreateMunicipalityRequest;
import it.unicam.cs.exploremunicipalities.controller.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/municipalities")
public class MunicipalityController {
    private final MunicipalityService municipalityService;

    @Autowired
    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @GetMapping()
    public ResponseEntity<Object> getMunicipalities() {
        try {
            return ResponseEntity.ok().body(this.municipalityService.getMunicipalities());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }











    @PostMapping()
    public ResponseEntity<Object> addMunicipality(@RequestBody CreateMunicipalityRequest request) {
        try {
            this.municipalityService.addMunicipality(request.name(), request.province());
            return ResponseEntity.ok().body("Municipality created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{municipalityId}")
    public ResponseEntity<Object> deleteMunicipality(@PathVariable long municipalityId) {
        try {
            this.municipalityService.removeMunicipality(municipalityId);
            return ResponseEntity.ok().body("Municipality deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
