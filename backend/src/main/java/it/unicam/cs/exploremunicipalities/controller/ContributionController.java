package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.config.JwtService;
import it.unicam.cs.exploremunicipalities.dto.request.*;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import it.unicam.cs.exploremunicipalities.service.ContributionService;
import it.unicam.cs.exploremunicipalities.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/contributions")
public class ContributionController {
    private final ContributionService contributionService;
    private final UserService userService;

    private final JwtService jwtService;

    @GetMapping("/{pointId}")
    public ResponseEntity<Object> getContributions(@PathVariable long pointId) {
        try {
            return ResponseEntity.ok(this.contributionService.getContributions(pointId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/details/{contributionId}")
    public ResponseEntity<Object> getContribution(@PathVariable long contributionId) {
        try {
            return ResponseEntity.ok(this.contributionService.getContributionDetails(contributionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/pointOfInterest")
    public ResponseEntity<Object> createPointOfInterest(@RequestBody CreatePOIForPointRequest request) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.createPointOfInterest(this.userService.getUser(userId), request.position(),
                    request.request().title(), request.request().description());
            return ResponseEntity.ok("Point of interest created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/event")
    public ResponseEntity<Object> createEvent(@RequestBody CreateEventForPointRequest request) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.createEvent(this.userService.getUser(userId), request.position(),
                    request.request().title(), request.request().description(), request.request().startDate(),
                    request.request().endDate());
            return ResponseEntity.ok("Event created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/itinerary/")
    public ResponseEntity<Object> createItinerary(@RequestBody CreateItineraryForPointRequest request) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.createItinerary(this.userService.getUser(userId), request.position(),
                    request.request().title(), request.request().description(), request.request().contributions());
            return ResponseEntity.ok("Itinerary created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<Object> getPendingContributions() {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            return ResponseEntity.ok(this.contributionService.getPendingContributions(this.userService.getUser(userId)
                    .getLicense()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/approve/{contributionId}")
    public ResponseEntity<Object> approveContribution(@PathVariable long contributionId) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.approveContribution(this.userService.getUser(userId).getLicense(), contributionId);
            return ResponseEntity.ok("Contribution approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{contributionId}")
    public ResponseEntity<Object> deleteContribution(@PathVariable long contributionId) {
        try {
            this.contributionService.deleteContribution(contributionId);
            return ResponseEntity.ok("Contribution deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/contest/poi/{contestId}")
    public ResponseEntity<Object> createPointOfInterestForContest(@PathVariable long contestId,
                                                                  @RequestBody CreatePOIRequest request) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.createPointOfInterestForContest(this.userService.getUser(userId),
                    contestId, request.title(), request.description());
            return ResponseEntity.ok("Point of interest added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/contest/event/{contestId}")
    public ResponseEntity<Object> createEventForContest(@PathVariable long contestId,
                                                        @RequestBody CreateEventRequest request) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.createEventForContest(this.userService.getUser(userId), contestId,
                    request.title(), request.description(), request.startDate(), request.endDate());
            return ResponseEntity.ok("Event added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/contest/itinerary/{contestId}")
    public ResponseEntity<Object> createItineraryForContest(@PathVariable long contestId,
                                                            @RequestBody CreateItineraryRequest request) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.createItineraryForContest(this.userService.getUser(userId), contestId,
                    request.title(), request.description(), request.contributions());
            return ResponseEntity.ok("Itinerary added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/publishWinningContribution/{contestId}")
    public ResponseEntity<Object> publishWinningContribution(@PathVariable long contestId) {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            this.contributionService.publishWinningContribution(this.userService.getUser(userId).getLicense(),
                    contestId);
            return ResponseEntity.ok("Winning contribution published successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
