package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.controller.dto.request.CreateMunicipalityRequest;
import it.unicam.cs.exploremunicipalities.controller.repository.MunicipalityRepository;
import it.unicam.cs.exploremunicipalities.controller.service.MunicipalityService;
import it.unicam.cs.exploremunicipalities.controller.service.PointService;
import it.unicam.cs.exploremunicipalities.controller.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/municipalities")
public class MunicipalityController {
    private final MunicipalityService municipalityService;
    private final RoleService roleService;

    @Autowired
    public MunicipalityController(MunicipalityService municipalityService, RoleService roleService) {
        this.municipalityService = municipalityService;
        this.roleService = roleService;
    }

    @GetMapping("/getMunicipalities")
    public ResponseEntity<Object> getMunicipalities() {
        try {
            return ResponseEntity.ok().body(this.municipalityService.getMunicipalities());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createMunicipality")
    public ResponseEntity<Object> createMunicipality(@RequestBody CreateMunicipalityRequest request) {
        try {
            this.municipalityService.createMunicipality(request.name(), request.province());
            return ResponseEntity.ok().body("Municipality created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/deleteMunicipality/{municipalityId}")
    public ResponseEntity<Object> deleteMunicipality(@PathVariable long municipalityId) {
        if (!this.roleService.getRoles(municipalityId).isEmpty()) {
            return ResponseEntity.badRequest().body("The municipality has roles associated with it");
        }
        try {
            this.municipalityService.deleteMunicipality(municipalityId);
            return ResponseEntity.ok().body("Municipality deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getRoles/{municipalityId}")
    public ResponseEntity<Object> getRoles(@PathVariable long municipalityId) {
        try {
            return ResponseEntity.ok().body(this.roleService.getRoles(municipalityId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
