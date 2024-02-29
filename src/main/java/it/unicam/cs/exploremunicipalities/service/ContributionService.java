package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.service.repository.ContributionRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.ContributionServiceInterface;
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

    @Override
    public Set<ContributionDTO> getContributions(long pointId) {
        Set<ContributionDTO> contributions = new HashSet<>();
        for (Contribution c : this.pointService.getPoint(pointId).getContributions()) {
            if (c.getState() == ContributionState.APPROVED) {
                contributions.add(c.toDTO());
            }
        }
        return contributions;
    }

    @Override
    public Contribution getContribution(long contributionId) {
        return this.contributionRepository.findById(contributionId).orElseThrow(() -> new IllegalArgumentException(
                "The contribution does not exist"));
    }

    @Override
    public ContributionDTO getContributionDetails(long contributionId) {
        return this.getContribution(contributionId).getDetails();
    }

    private void createContribution(License license, Coordinate position, Contribution contribution) throws Exception {
        Point p = this.pointService.associatePoint(position, license.getMunicipality());
        if (license.getRole() == UserRole.CURATOR || license.getRole() == UserRole.AUTHORIZED_CONTRIBUTOR) {
            p.addContribution(contribution);
        } else if (license.getRole() == UserRole.CONTRIBUTOR) {
            p.addPendingContribution(contribution);
        } else {
            throw new Exception("The user is not authorized to create a contribution");
        }
        this.contributionRepository.save(contribution);
    }

    @Override
    public void createPointOfInterest(User user, Coordinate position, String title, String description) throws Exception {
        PointOfInterest poi = new PointOfInterest(title, description, null, user);
        this.createContribution(user.getLicense(), position, poi);
    }

    @Override
    public void createEvent(User user, Coordinate position, String title, String description, LocalDateTime startDate,
                            LocalDateTime endDate) throws Exception {
        Event event = new Event(title, description, null, user, startDate, endDate);
        this.createContribution(user.getLicense(), position, event);
    }

    @Override
    public void createItinerary(User user, Coordinate position, String title, String description,
                                Set<Long> contributions) throws Exception {
        Itinerary itinerary = itineraryFactory(user, title, description, contributions);
        this.createContribution(user.getLicense(), position, itinerary);
    }

    @Override
    public Set<ContributionDTO> getPendingContributions(License license) {
        if (license.getRole() != UserRole.CURATOR) {
            throw new IllegalArgumentException("The user is not authorized to get the pending contributions");
        }
        Set<ContributionDTO> contributions = new HashSet<>();
        for (Point p : license.getMunicipality().getPoints()) {
            for (ContributionDTO c : this.getContributions(p.getId())) {
                if (c.getState() == ContributionState.PENDING) {
                    contributions.add(c);
                }
            }
        }
        return contributions;
    }

    @Override
    public void approveContribution(License license, long contributionId) {
        if (!this.checkAuthorization(license, contributionId)) {
            throw new IllegalArgumentException("The user is not authorized to approve the contribution");
        }
        Contribution contribution = this.getContribution(contributionId);
        contribution.setState(ContributionState.APPROVED);
        this.contributionRepository.save(contribution);
    }

    private boolean checkAuthorization(License license, long contributionId) {
        return this.getPendingContributions(license).stream().anyMatch(c -> c.getId() == contributionId);
    }

    @Override
    public void deleteContribution(long contributionId) {
        this.contributionRepository.delete(this.getContribution(contributionId));
    }

    private void createContributionForContest(License license, long contestId, Contribution contribution) throws Exception {
        Contest c = this.contestService.getContest(contestId);
        if (license.getRole() == UserRole.CURATOR || license.getRole() == UserRole.AUTHORIZED_CONTRIBUTOR ||
                license.getRole() == UserRole.CONTRIBUTOR) {
            c.addContribution(contribution);
        } else {
            throw new Exception("The user is not authorized to create a contribution");
        }
        this.contributionRepository.save(contribution);
    }

    @Override
    public void createPointOfInterestForContest(User user, long contestId, String title, String description) throws Exception {
        PointOfInterest poi = new PointOfInterest(title, description, null, user);
        this.createContributionForContest(user.getLicense(), contestId, poi);
    }

    @Override
    public void createEventForContest(User user, long contestId, String title, String description, LocalDateTime startDate,
                            LocalDateTime endDate) throws Exception {
        Event event = new Event(title, description, null, user, startDate, endDate);
        this.createContributionForContest(user.getLicense(), contestId, event);
    }

    @Override
    public void createItineraryForContest(User user, long contestId, String title, String description,
                                Set<Long> contributions) throws Exception {
        Itinerary itinerary = itineraryFactory(user, title, description, contributions);
        this.createContributionForContest(user.getLicense(), contestId, itinerary);
    }

    private Itinerary itineraryFactory(User user, String title, String description, Set<Long> contributions) {
        if (contributions.size() < 2) {
            throw new IllegalArgumentException("An itinerary must have at least two contributions");
        }
        Set<Contribution> c = new HashSet<>();
        for (long id : contributions) {
            c.add(this.getContribution(id));
        }
        return new Itinerary(title, description, null, user, c);
    }

    @Override
    public void publishWinningContribution(License license, long contestId) throws Exception {
        Contest c = this.contestService.getContest(contestId);
        if (c.getWinner() == null) {
            throw new IllegalArgumentException("The contest does not have a winner");
        }
        this.createContribution(license, c.getPosition(), c.getWinner());
    }
}
