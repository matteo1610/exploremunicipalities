package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.controller.repository.ContributionRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.ContributionServiceInterface;
import it.unicam.cs.exploremunicipalities.model.content.Contest;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.content.contribution.*;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class ContributionService implements ContributionServiceInterface {
    private final ContributionRepository contributionRepository;
    private final PointService pointService;
    private final ContestService contestService;

    public ContributionService(ContributionRepository contributionRepository, PointService pointService,
                               ContestService contestService) {
        this.contributionRepository = contributionRepository;
        this.pointService = pointService;
        this.contestService = contestService;
    }

    /**
     * Returns the details of the contributions associated with the point.
     * @param pointId the id of the point
     * @return the details of the contributions associated with the point
     */
    public Set<ContributionDTO> getContributions(long pointId) {
        Set<ContributionDTO> contributions = new HashSet<>();
        for (Contribution c : this.pointService.getPoint(pointId).getContributions()) {
            contributions.add(c.toDTO());
        }
        return contributions;
    }

    /**
     * Returns a contribution with the given id.
     * @param contributionId the id of the contribution to get
     * @return a contribution with the given id
     * @throws IllegalArgumentException if the contribution does not exist
     */
    public Contribution getContribution(long contributionId) {
        return this.contributionRepository.findById(contributionId).orElseThrow(() -> new IllegalArgumentException(
                "The contribution does not exist"));
    }

    private void createContribution(License license, Coordinate position, Contribution contribution) throws Exception {
        Point p = this.pointService.associatePoint(position, license.getMunicipality().getId());
        if (license.getRole() == UserRole.CURATOR || license.getRole() == UserRole.AUTHORIZED_CONTRIBUTOR) {
            p.addContribution(contribution);
        } else if (license.getRole() == UserRole.CONTRIBUTOR) {
            p.addPendingContribution(contribution);
        } else {
            throw new Exception("The user is not authorized to create a contribution");
        }
    }

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
    public void createPointOfInterest(User user, Coordinate position, String title, String description) throws Exception {
        PointOfInterest poi = new PointOfInterest(title, description, null, user);
        this.createContribution(user.getLicense(), position, poi);
        this.contributionRepository.save(poi);
    }

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
    public void createEvent(User user, Coordinate position, String title, String description, LocalDateTime startDate,
                            LocalDateTime endDate) throws Exception {
        Event event = new Event(title, description, null, user, startDate, endDate);
        this.createContribution(user.getLicense(), position, event);
        this.contributionRepository.save(event);
    }

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
    public void createItinerary(User user, Coordinate position, String title, String description,
                                Set<Long> contributions) throws Exception {
        if (contributions.size() < 2) {
            throw new IllegalArgumentException("An itinerary must have at least two contributions");
        }
        Set<Contribution> c = new HashSet<>();
        for (long id : contributions) {
            c.add(this.getContribution(id));
        }
        Itinerary itinerary = new Itinerary(title, description, null, user, c);
        this.createContribution(user.getLicense(), position, itinerary);
        this.contributionRepository.save(itinerary);
    }

    /**
     * Returns the pending contributions of the municipality.
     * @param license the license of the user
     * @return the pending contributions of the municipality.
     */
    public Set<ContributionDTO> getPendingContributions(License license) {
        if (license.getRole() != UserRole.CURATOR) {
            throw new IllegalArgumentException("The user is not authorized to get the pending contributions");
        }
        Set<ContributionDTO> contributions = new HashSet<>();
        for (Contribution c : this.pointService.getPoint(license.getMunicipality().getId()).getContributions()) {
            if (c.getState() == ContributionState.PENDING) {
                contributions.add(c.toDTO());
            }
        }
        return contributions;
    }

    /**
     * Approves a contribution in the repository.
     * @param license the license of the user
     * @param contributionId the id of the contribution to approve
     * @throws IllegalArgumentException if the user is not authorized to approve the contribution
     */
    public void approveContribution(License license, long contributionId) {
        if (!this.checkAuthorization(license, contributionId)) {
            throw new IllegalArgumentException("The user is not authorized to approve the contribution");
        }
        Contribution contribution = this.getContribution(contributionId);
        contribution.setState(ContributionState.APPROVED);
    }

    private boolean checkAuthorization(License license, long contributionId) {
        return this.getPendingContributions(license).stream().anyMatch(c -> c.id() == contributionId);
    }

    /**
     * Removes a contribution in the repository.
     * @param contributionId the id of the contribution to remove
     * @throws IllegalArgumentException if the contribution does not exist
     */
    public void removeContribution(long contributionId) {
        this.contributionRepository.delete(this.getContribution(contributionId));
    }

    /**
     * Publishes the winning contribution of the contest.
     * @param license the license of the user
     * @param contestId the id of the contest
     * @throws IllegalArgumentException if the contribution does not belong to the selected contest
     */
    public void publishWinningContribution(License license, long contestId) throws Exception {
        Contest c = this.contestService.getContest(contestId);
        if (c.getWinner() == null) {
            throw new IllegalArgumentException("The contest does not have a winner");
        }
        this.createContribution(license, c.getPosition(), c.getWinner());
    }
}
