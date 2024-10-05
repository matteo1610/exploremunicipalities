package it.unicam.cs.exploremunicipalities.service.abstractions;

import it.unicam.cs.exploremunicipalities.dto.entities.ContributionDTO;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

import java.time.LocalDateTime;
import java.util.Set;

public interface ContributionServiceInterface {
    /**
     * Returns the details of the contributions associated with the point.
     * @param pointId the id of the point
     * @return the details of the contributions associated with the point
     */
    Set<ContributionDTO> getContributions(long pointId);

    /**
     * Returns a contribution with the given id.
     * @param contributionId the id of the contribution to get
     * @return a contribution with the given id
     * @throws IllegalArgumentException if the contribution does not exist
     */
    Contribution getContribution(long contributionId);

    /**
     * Returns the details of the contribution with the given id.
     * @param contributionId the id of the contribution
     * @return the details of the contribution with the given id
     */
    ContributionDTO getContributionDetails(long contributionId);

    /**
     * Creates a new point of interest.
     * @param user the user creating the point of interest
     * @param position the position of the point of interest
     * @param title the title of the point of interest
     * @param description the description of the point of interest
     * @throws IllegalArgumentException if the municipality does not exist, or if the point is not in the municipality
     * @throws Exception if an error occurs while getting information of the position from the OSM service
     * @throws Exception if the user is not authorized to create a contribution
     */
    void createPointOfInterest(User user, Coordinate position, String title, String description) throws Exception;

    /**
     * Creates a new event.
     * @param user the user creating the event
     * @param position the position of the event
     * @param title the title of the event
     * @param description the description of the event
     * @param startDate the start date of the event
     * @param endDate the end date of the event
     * @throws IllegalArgumentException if the municipality does not exist, or if the point is not in the municipality
     * @throws Exception if an error occurs while getting information of the position from the OSM service
     * @throws Exception if the user is not authorized to create a contribution
     */
    void createEvent(User user, Coordinate position, String title, String description, LocalDateTime startDate,
                     LocalDateTime endDate) throws Exception;

    /**
     * Creates a new itinerary.
     * @param user the user creating the itinerary
     * @param position the position of the itinerary
     * @param title the title of the itinerary
     * @param description the description of the itinerary
     * @param contributions the contributions of the itinerary
     * @throws IllegalArgumentException if the municipality does not exist, or if the point is not in the municipality
     * @throws IllegalArgumentException if the itinerary does not have at least two contributions
     * @throws IllegalArgumentException if the contribution does not exist
     * @throws Exception if an error occurs while getting information of the position from the OSM service
     * @throws Exception if the user is not authorized to create a contribution
     */
    void createItinerary(User user, Coordinate position, String title, String description, Set<Long> contributions)
            throws Exception;

    /**
     * Returns the pending contributions of the municipality.
     * @param license the license of the user
     * @return the pending contributions of the municipality.
     */
    Set<ContributionDTO> getPendingContributions(License license);

    /**
     * Approves a contribution in the repository.
     * @param license the license of the user
     * @param contributionId the id of the contribution to approve
     * @throws IllegalArgumentException if the user is not authorized to approve the contribution
     */
    void approveContribution(License license, long contributionId);

    /**
     * Removes a contribution in the repository.
     * @param contributionId the id of the contribution to remove
     * @throws IllegalArgumentException if the contribution does not exist
     */
    void deleteContribution(long contributionId);

    /**
     * Publishes the winning contribution of the contest.
     * @param license the license of the user
     * @param contestId the id of the contest
     * @throws IllegalArgumentException if the contribution does not belong to the selected contest
     */
    void publishWinningContribution(License license, long contestId) throws Exception;

    /**
     * Create a point of interest for a contest
     * @param user the user who creates the contribution
     * @param contestId the contest id
     * @param title the title of the point of interest
     * @param description the description of the point of interest
     * @throws Exception if the user is not authorized to create the contribution
     */
    void createPointOfInterestForContest(User user, long contestId, String title, String description) throws Exception;

    /**
     * Create an event for a contest
     * @param user the user who creates the contribution
     * @param contestId the contest id
     * @param title the title of the event
     * @param description the description of the event
     * @param startDate the start date of the event
     * @param endDate the end date of the event
     * @throws Exception if the user is not authorized to create the contribution
     */
    void createEventForContest(User user, long contestId, String title, String description, LocalDateTime startDate,
                                      LocalDateTime endDate) throws Exception;


    /**
     * Create an itinerary for a contest
     * @param user the user who creates the contribution
     * @param contestId the contest id
     * @param title the title of the itinerary
     * @param description the description of the itinerary
     * @param contributions the contributions of the itinerary
     * @throws Exception if the user is not authorized to create the contribution
     */
    void createItineraryForContest(User user, long contestId, String title, String description,
                                   Set<Long> contributions) throws Exception;
}
