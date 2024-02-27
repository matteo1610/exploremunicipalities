package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.ContestDTO;
import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
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
public class ContestService {
    private final ContestRepository contestRepository;

    public ContestService(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    /**
     * Returns all the contests of the municipality associated with the given license.
     * @param license the license of the user
     * @return all the contests of the municipality associated with the given license
     * @throws IllegalArgumentException if the authenticated tourist is not authorized to get the contests
     */
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

    /**
     * Returns a contest with the given id.
     * @param contestId the id of the contest to get
     * @return a contest with the given id
     * @throws IllegalArgumentException if the contest does not exist
     */
    public Contest getContest(long contestId) {
        return this.contestRepository.findById(contestId).orElseThrow(() -> new IllegalArgumentException(
                "The contest does not exist"));
    }

    /**
     * Creates a new contest.
     * @param license the license of the user
     * @param title the title of the contest
     * @param description the description of the contest
     * @param position the position of the contest
     * @throws IllegalArgumentException if the user is not authorized to create a contest
     */
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

    /**
     * Opens a contest with the given id.
     * @param license the license of the user
     * @param contestId the id of the contest to open
     * @throws IllegalArgumentException if the user is not authorized, or if the contest is not created
     */
    public void openContest(License license, long contestId) {
        this.checkAuthorization(license, contestId);
        Contest contest = this.getContest(contestId);
        if (contest.getState() != ContestState.CREATED) {
            throw new IllegalArgumentException("The contest is already open or closed");
        }
    }

    /**
     * Closes a contest with the given id.
     * @param license the license of the user
     * @param contestId the id of the contest to close
     * @throws IllegalArgumentException if the user is not authorized, or if the contest is not open
     */
    public void closeContest(License license, long contestId) {
        this.checkAuthorization(license, contestId);
        Contest contest = this.getContest(contestId);
        if (contest.getState() != ContestState.OPEN) {
            throw new IllegalArgumentException("The contest is not open or already closed");
        }
    }

    /**
     * Returns the details of the contributions associated with the contest.
     * @param contestId the id of the contest
     * @return the details of the contributions associated with the contest
     */
    public Set<ContributionDTO> getContestContributions(License license, long contestId) {
        this.checkAuthorization(license, contestId);
        Set<ContributionDTO> contributions = new HashSet<>();
        for (Contribution c : this.getContest(contestId).getContributions()) {
            contributions.add(c.toDTO());
        }
        return contributions;
    }

    /**
     * Publishes the winning contribution of the contest with the given id.
     * @param license the license of the user
     * @param contestId the id of the contest to publish the winning contribution
     */
    public void setWinner(License license, long contestId, long contributionId) {
        this.checkAuthorization(license, contestId);
        Contest contest = this.getContest(contestId);
        if (contest.getState() != ContestState.CLOSED) {
            throw new IllegalArgumentException("The contest is not yet closed");
        }
        contest.setWinner(contest.getContributions().stream().filter(c -> c.getId() == contributionId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The contribution does not exist")));
        // TODO: notificare il vincitore
    }

    /**
     * Deletes a contest with the given id.
     * @param contestId the id of the contest to delete
     */
    public void deleteContest(License license, long contestId) {
        this.checkRole(license.getRole());
        this.contestRepository.delete(this.getContest(contestId));
    }
}
