package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.model.content.Contest;
import it.unicam.cs.exploremunicipalities.model.content.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.ContributionState;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A service for managing content of a municipality.
 */
public class ContentService {
    private final Municipality municipality;
    private final Map<UUID, Contribution> contributionRepository;
    private final Map<UUID, Contest> contestRepository;
    private final RoleService roleService;

    public ContentService(Municipality municipality, Map<UUID, Contribution> contributionRepository,
                          Map<UUID, Contest> contestRepository) {
        this.municipality = municipality;
        this.contributionRepository = contributionRepository;
        this.contestRepository = contestRepository;
        this.roleService = RoleService.getInstance();
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
    public boolean addContribution(String title, String description, List<File> multimedia, User author) {
        // TODO: finish implementation (check point is in municipality, check license, add contribution)
        License license = this.roleService.getLicense(author, this.municipality);
        if (license.canContribute()) {
            Contribution contribution = new Contribution(title, description, multimedia, ContributionState.APPROVED,
                    author, municipality);
            this.contributionRepository.put(contribution.getId(), contribution);
            return true;
        }
        if (license.canRequestContribute()) {
            Contribution contribution = new Contribution(title, description, multimedia, ContributionState.PENDING,
                    author, municipality);
            this.contributionRepository.put(contribution.getId(), contribution);
            return true;
        }
        return false;
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
        return this.contributionRepository.values().stream()
            .filter(contribution -> contribution.getState() == ContributionState.PENDING &&
                    contribution.getMunicipality() == this.municipality)
            .collect(Set::of, Set::add, Set::addAll);
    }
}
