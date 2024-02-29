package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.dto.request.*;
import it.unicam.cs.exploremunicipalities.service.ContributionService;
import it.unicam.cs.exploremunicipalities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contributions")
public class ContributionController {
    private final ContributionService contributionService;
    private final UserService userService;

    @Autowired
    public ContributionController(ContributionService contributionService, UserService userService) {
        this.contributionService = contributionService;
        this.userService = userService;
    }

    @GetMapping("/getContributions/{pointId}")
    public ResponseEntity<Object> getContributions(@PathVariable long pointId) {
        try {
            return ResponseEntity.ok(this.contributionService.getContributions(pointId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getContribution/{contributionId}")
    public ResponseEntity<Object> getContribution(@PathVariable long contributionId) {
        try {
            return ResponseEntity.ok(this.contributionService.getContributionDetails(contributionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContribution/pointOfInterest/{userId}")
    public ResponseEntity<Object> createPointOfInterest(@PathVariable long userId,
                                                        @RequestBody CreatePOIRequest request) {
        try {
            this.contributionService.createPointOfInterest(this.userService.getUser(userId), request.coordinate(),
                    request.title(), request.description());
            return ResponseEntity.ok("Point of interest created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContribution/event/{userId}")
    public ResponseEntity<Object> createEvent(@PathVariable long userId,
                                              @RequestBody CreateEventRequest request) {
        try {
            this.contributionService.createEvent(this.userService.getUser(userId), request.coordinate(),
                    request.title(), request.description(), request.startDate(), request.endDate());
            return ResponseEntity.ok("Event created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContribution/itinerary/{userId}")
    public ResponseEntity<Object> createItinerary(@PathVariable long userId,
                                                  @RequestBody CreateItineraryRequest request) {
        try {
            this.contributionService.createItinerary(this.userService.getUser(userId), request.coordinate(),
                    request.title(), request.description(), request.contributions());
            return ResponseEntity.ok("Itinerary created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getPendingContributions/{userId}")
    public ResponseEntity<Object> getPendingContributions(@PathVariable long userId) {
        try {
            return ResponseEntity.ok(this.contributionService.getPendingContributions(this.userService.getUser(userId)
                    .getLicense()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/approveContribution/{userId}/{contributionId}")
    public ResponseEntity<Object> approveContribution(@PathVariable long userId, @PathVariable long contributionId) {
        try {
            this.contributionService.approveContribution(this.userService.getUser(userId).getLicense(), contributionId);
            return ResponseEntity.ok("Contribution approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteContribution/{contributionId}")
    public ResponseEntity<Object> deleteContribution(@PathVariable long contributionId) {
        try {
            this.contributionService.deleteContribution(contributionId);
            return ResponseEntity.ok("Contribution deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContribution/contest/pointOfInterest/{userId}")
    public ResponseEntity<Object> createPointOfInterestForContest(@PathVariable long userId ,
                                                                  @RequestBody CreatePOIContestRequest request) {
        try {
            this.contributionService.createPointOfInterestForContest(this.userService.getUser(userId),
                    request.contestId(), request.title(), request.description());
            return ResponseEntity.ok("Point of interest added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContribution/contest/event/{userId}")
    public ResponseEntity<Object> createEventForContest(@PathVariable long userId,
                                                       @RequestBody CreateEventForContestRequest request) {
        try {
            this.contributionService.createEventForContest(this.userService.getUser(userId), request.contestId(),
                    request.title(), request.description(), request.startDate(), request.endDate());
            return ResponseEntity.ok("Event added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContribution/contest/itinerary/{userId}")
    public ResponseEntity<Object> createItineraryForContest(@PathVariable long userId,
                                                           @RequestBody CreateItineraryForContestRequest request) {
        try {
            this.contributionService.createItineraryForContest(this.userService.getUser(userId), request.contestId(),
                    request.title(), request.description(), request.contributions());
            return ResponseEntity.ok("Itinerary added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
