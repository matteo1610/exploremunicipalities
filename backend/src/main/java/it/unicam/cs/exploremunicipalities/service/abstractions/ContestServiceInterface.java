package it.unicam.cs.exploremunicipalities.service.abstractions;

import it.unicam.cs.exploremunicipalities.dto.ContestDTO;
import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.model.content.Contest;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

import java.util.Set;

public interface ContestServiceInterface {
    /**
     * Returns all the contests of the municipality associated with the given license.
     * @param license the license of the user
     * @return all the contests of the municipality associated with the given license
     * @throws IllegalArgumentException if the authenticated tourist is not authorized to get the contests
     */
    Set<ContestDTO> getContests(License license);

    /**
     * Returns a contest with the given id.
     * @param contestId the id of the contest to get
     * @return a contest with the given id
     * @throws IllegalArgumentException if the contest does not exist
     */
    Contest getContest(long contestId);

    /**
     * Creates a new contest.
     * @param license the license of the user
     * @param title the title of the contest
     * @param description the description of the contest
     * @param position the position of the contest
     * @throws IllegalArgumentException if the user is not authorized to create a contest
     */
    void createContest(License license, String title, String description, Coordinate position);

    /**
     * Opens a contest with the given id.
     * @param license the license of the user
     * @param contestId the id of the contest to open
     * @throws IllegalArgumentException if the user is not authorized, or if the contest is not created
     */
    void openContest(License license, long contestId);

    /**
     * Closes a contest with the given id.
     * @param license the license of the user
     * @param contestId the id of the contest to close
     * @throws IllegalArgumentException if the user is not authorized, or if the contest is not open
     */
    void closeContest(License license, long contestId);

    /**
     * Returns the details of the contributions associated with the contest.
     * @param contestId the id of the contest
     * @return the details of the contributions associated with the contest
     */
    Set<ContributionDTO> getContestContributions(License license, long contestId);

    /**
     * Publishes the winning contribution of the contest with the given id.
     * @param license the license of the user
     * @param contestId the id of the contest to publish the winning contribution
     */
    void setWinner(License license, long contestId, long contributionId);

    /**
     * Deletes a contest with the given id.
     * @param contestId the id of the contest to delete
     */
    void deleteContest(License license, long contestId);
}
