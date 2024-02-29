package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.ContestDTO;
import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import it.unicam.cs.exploremunicipalities.model.user.Notification;
import it.unicam.cs.exploremunicipalities.service.abstractions.ContestServiceInterface;
import it.unicam.cs.exploremunicipalities.service.repository.ContestRepository;
import it.unicam.cs.exploremunicipalities.model.content.Contest;
import it.unicam.cs.exploremunicipalities.model.content.ContestState;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContestService implements ContestServiceInterface {
    private final ContestRepository contestRepository;
    private final NotificationService notificationService;

    public ContestService(ContestRepository contestRepository, NotificationService notificationService) {
        this.contestRepository = contestRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Set<ContestDTO> getContests(License license) {
        if (license == null) {
            throw new IllegalArgumentException("The authenticated tourist is not authorized to get the contests");
        }
        Set<ContestDTO> contests = new HashSet<>();
        for (Contest c : license.getMunicipality().getContests()) {
            contests.add(c.toDTO());
        }
        return contests;
    }

    private void checkRole(UserRole role) {
        if (role != UserRole.ANIMATOR) {
            throw new IllegalArgumentException("The user is not authorized to create a contest");
        }
    }

    @Override
    public Contest getContest(long contestId) {
        return this.contestRepository.findById(contestId).orElseThrow(() -> new IllegalArgumentException(
                "The contest does not exist"));
    }

    @Override
    public void createContest(License license, String title, String description, Coordinate position) {
        // la posizione viene controllata nella pubblicazione del contributo vincente
        this.checkRole(license.getRole());
        Contest contest = new Contest(title, description, position);
        license.getMunicipality().addContest(contest);
        this.contestRepository.save(contest);
    }

    private void checkAuthorization(License license, long contestId) {
        if (this.getContests(license).stream().noneMatch(c -> c.id() == contestId)) {
            throw new IllegalArgumentException("The user is not authorized to manage the contest");
        }
    }

    @Override
    public void openContest(License license, long contestId) {
        this.checkAuthorization(license, contestId);
        Contest contest = this.getContest(contestId);
        if (contest.getState() != ContestState.CREATED) {
            throw new IllegalArgumentException("The contest is already open or closed");
        }
        contest.setState(ContestState.OPEN);
        this.contestRepository.save(contest);
    }

    @Override
    public void closeContest(License license, long contestId) {
        this.checkAuthorization(license, contestId);
        Contest contest = this.getContest(contestId);
        if (contest.getState() != ContestState.OPEN) {
            throw new IllegalArgumentException("The contest is not open or already closed");
        }
        contest.setState(ContestState.CLOSED);
        this.contestRepository.save(contest);
    }

    @Override
    public Set<ContributionDTO> getContestContributions(License license, long contestId) {
        this.checkAuthorization(license, contestId);
        Set<ContributionDTO> contributions = new HashSet<>();
        for (Contribution c : this.getContest(contestId).getContributions()) {
            contributions.add(c.toDTO());
        }
        return contributions;
    }

    @Override
    public void setWinner(License license, long contestId, long contributionId) {
        this.checkAuthorization(license, contestId);
        Contest contest = this.getContest(contestId);
        if (contest.getState() != ContestState.CLOSED) {
            throw new IllegalArgumentException("The contest is not yet closed");
        }
        contest.setWinner(contest.getContributions().stream().filter(c -> c.getId() == contributionId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The contribution does not exist")));
        this.contestRepository.save(contest);
        this.notificationService.createNotification(contest.getWinner().getAuthor(), new Notification(
                "You won the contest " + contest.getTitle() + " (id: " + contest.getId() + ")"));
    }

    @Override
    public void deleteContest(License license, long contestId) {
        this.checkRole(license.getRole());
        this.contestRepository.delete(this.getContest(contestId));
    }
}
