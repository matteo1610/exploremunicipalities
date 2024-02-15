package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.model.content.*;
import it.unicam.cs.exploremunicipalities.model.content.contest.Contest;
import it.unicam.cs.exploremunicipalities.model.service.OSMProxy;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
import it.unicam.cs.exploremunicipalities.model.service.OSMServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.*;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A service for managing content of a municipality.
 */
public class ContentService {
    private final Municipality municipality;
    private final Map<UUID, Point> pointRepository;
    private final Map<UUID, Contribution> contributionRepository;
    private final Map<UUID, Contest> contestRepository;
    private final RoleService roleService;
    private final OSMServiceInterface osmService;

    public ContentService(Municipality municipality, Map<UUID, Point> pointRepository,
                          Map<UUID, Contribution> contributionRepository, Map<UUID, Contest> contestRepository) {
        this.municipality = municipality;
        this.pointRepository = pointRepository;
        this.contributionRepository = contributionRepository;
        this.contestRepository = contestRepository;
        this.roleService = RoleService.getInstance();
        this.osmService = new OSMProxy(new OSMService());
    }

    /**
     * Returns the municipality.
     * @return the municipality
     */
    public Municipality getMunicipality() {
        return this.municipality;
    }

    /**
     * Returns a contribution with the given id.
     * @param id the id of the contribution to get
     * @return a contribution with the given id
     */
    public Contribution getContributionById(UUID id) {
        return this.contributionRepository.get(id);
    }

    /**
     * Adds a contribution with the given title, description, multimedia and author.
     * @param title the title of the contribution
     * @param description the description of the contribution
     * @param multimedia the multimedia files of the contribution
     * @param author the author of the contribution
     * @return true if the contribution was added, false otherwise
     */
    public boolean addPointOfInterest(String title, String description, Set<File> multimedia, User author,
                                      CoordinatePoint point) throws Exception {
        Point p = this.getPoint(point, this.municipality);
        if (p != null) {
            this.pointRepository.get(p.getId()).addContribution(new PointOfInterest(title, description, multimedia,
                    ContributionState.APPROVED, author));
            return true;
        } else {
            p = new Point(point, this.municipality);
            if (this.osmService.isPointInMunicipality(point, this.municipality)) {
                p.addContribution(new PointOfInterest(title, description, multimedia, ContributionState.APPROVED, author));
                this.pointRepository.put(p.getId(), p);
                return true;
            } else {
                return false;
            }
        }
    }

    private Point getPoint(CoordinatePoint point, Municipality municipality) {
        Point np = new Point(point, municipality);
        return this.pointRepository.values().stream()
                .filter(p -> p.equals(np))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a contest with the given id.
     * @param id the id of the contest to get
     * @return a contest with the given id
     */
    public Contest getContestById(UUID id) {
        return this.contestRepository.get(id);
    }

    /**
     * Returns all pending contributions of the municipality.
     * @return all pending contributions of the municipality
     */
    public Set<Contribution> getAllPendingContributions() {
        // TODO: finish implementation
        return null;
    }
}
