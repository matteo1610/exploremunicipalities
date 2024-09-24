package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.dto.request.*;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import it.unicam.cs.exploremunicipalities.service.ContributionService;
import it.unicam.cs.exploremunicipalities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/contributions")
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

    @PostMapping("/createPointOfInterest/{userId}")
    public ResponseEntity<Object> createPointOfInterest(@PathVariable long userId,
                                                        @RequestBody CreatePOIForPointRequest request) {
        try {
            this.contributionService.createPointOfInterest(this.userService.getUser(userId), request.position(),
                    request.request().title(), request.request().description());
            return ResponseEntity.ok("Point of interest created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createEvent/{userId}")
    public ResponseEntity<Object> createEvent(@PathVariable long userId,
                                              @RequestBody CreateEventForPointRequest request) {
        try {
            this.contributionService.createEvent(this.userService.getUser(userId), request.position(),
                    request.request().title(), request.request().description(), request.request().startDate(),
                    request.request().endDate());
            return ResponseEntity.ok("Event created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createItinerary/{userId}")
    public ResponseEntity<Object> createItinerary(@PathVariable long userId,
                                                  @RequestBody CreateItineraryForPointRequest request) {
        try {
            this.contributionService.createItinerary(this.userService.getUser(userId), request.position(),
                    request.request().title(), request.request().description(), request.request().contributions());
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

    @PostMapping("/contest/createPointOfInterest/{userId}/{contestId}")
    public ResponseEntity<Object> createPointOfInterestForContest(@PathVariable long userId,
                                                                  @PathVariable long contestId,
                                                                  @RequestBody CreatePOIRequest request) {
        try {
            this.contributionService.createPointOfInterestForContest(this.userService.getUser(userId),
                    contestId, request.title(), request.description());
            return ResponseEntity.ok("Point of interest added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/contest/createEvent/{userId}/{contestId}")
    public ResponseEntity<Object> createEventForContest(@PathVariable long userId, @PathVariable long contestId,
                                                        @RequestBody CreateEventRequest request) {
        try {
            this.contributionService.createEventForContest(this.userService.getUser(userId), contestId,
                    request.title(), request.description(), request.startDate(), request.endDate());
            return ResponseEntity.ok("Event added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/contest/createItinerary/{userId}/{contestId}")
    public ResponseEntity<Object> createItineraryForContest(@PathVariable long userId, @PathVariable long contestId,
                                                            @RequestBody CreateItineraryRequest request) {
        try {
            this.contributionService.createItineraryForContest(this.userService.getUser(userId), contestId,
                    request.title(), request.description(), request.contributions());
            return ResponseEntity.ok("Itinerary added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/publishWinningContribution/{userId}/{contestId}")
    public ResponseEntity<Object> publishWinningContribution(@PathVariable long userId, @PathVariable long contestId) {
        try {
            this.contributionService.publishWinningContribution(this.userService.getUser(userId).getLicense(),
                    contestId);
            return ResponseEntity.ok("Winning contribution published successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
