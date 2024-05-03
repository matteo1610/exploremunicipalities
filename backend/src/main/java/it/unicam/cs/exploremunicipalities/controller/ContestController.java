package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.dto.request.CreateContestRequest;
import it.unicam.cs.exploremunicipalities.service.ContestService;
import it.unicam.cs.exploremunicipalities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contests")
public class ContestController {
    private final ContestService contestService;
    private final UserService userService;

    @Autowired
    public ContestController(ContestService contestService, UserService userService) {
        this.contestService = contestService;
        this.userService = userService;
    }

    @GetMapping("/getContests/{userid}")
    public ResponseEntity<Object> getContests(@PathVariable long userid) {
        try {
            return ResponseEntity.ok(this.contestService.getContests(this.userService.getUser(userid).getLicense()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createContest/{userId}")
    public ResponseEntity<Object> createContest(@PathVariable long userId, @RequestBody CreateContestRequest request) {
        try {
            this.contestService.createContest(this.userService.getUser(userId).getLicense(), request.title(),
                    request.description(), request.position());
            return ResponseEntity.ok("Contest created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/openContest/{userId}/{contestId}")
    public ResponseEntity<Object> openContest(@PathVariable long userId, @PathVariable long contestId) {
        try {
            this.contestService.openContest(this.userService.getUser(userId).getLicense(), contestId);
            return ResponseEntity.ok("Contest opened successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/closeContest/{userId}/{contestId}")
    public ResponseEntity<Object> closeContest(@PathVariable long userId, @PathVariable long contestId) {
        try {
            this.contestService.closeContest(this.userService.getUser(userId).getLicense(), contestId);
            return ResponseEntity.ok("Contest closed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getContestContributions/{userId}/{contestId}")
    public ResponseEntity<Object> getContestContributions(@PathVariable long userId, @PathVariable long contestId) {
        try {
            return ResponseEntity.ok(this.contestService.getContestContributions(this.userService.getUser(userId)
                    .getLicense(), contestId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/setWinner/{userId}/{contestId}/{contributionId}")
    public ResponseEntity<Object> setWinner(@PathVariable long userId, @PathVariable long contestId,
                                            @PathVariable long contributionId) {
        try {
            this.contestService.setWinner(this.userService.getUser(userId).getLicense(), contestId, contributionId);
            return ResponseEntity.ok("Winner set successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteContest/{userId}/{contestId}")
    public ResponseEntity<Object> deleteContest(@PathVariable long userId, @PathVariable long contestId) {
        try {
            this.contestService.deleteContest(this.userService.getUser(userId).getLicense(), contestId);
            return ResponseEntity.ok("Contest deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
