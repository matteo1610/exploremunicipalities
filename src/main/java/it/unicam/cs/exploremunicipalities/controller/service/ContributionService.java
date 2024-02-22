package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.controller.repository.ContributionRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.ContributionServiceInterface;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContributionService implements ContributionServiceInterface {
    private final ContributionRepository contributionRepository;
    private final PointService pointService;

    public ContributionService(ContributionRepository contributionRepository, PointService pointService) {
        this.contributionRepository = contributionRepository;
        this.pointService = pointService;
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
    public void getContribution(long contributionId) {
        this.contributionRepository.findById(contributionId).orElseThrow();
    }





    /*
    public void addContributionToPoint(CoordinatePoint position, Municipality municipality, Contribution contribution)
            throws Exception {
        this.checkPoint(position, municipality).addContribution(contribution);
        this.contributionRepository.save(contribution);
    }

    public void addPendingContributionToPoint(CoordinatePoint position, Municipality municipality, Contribution contribution)
            throws Exception {
        this.checkPoint(position, municipality).addPendingContribution(contribution);
        this.contributionRepository.save(contribution);
    }
     */

    /**
     * Removes a contribution in the repository.
     * @param contributionId the id of the contribution to remove
     * @throws IllegalArgumentException if the contribution does not exist
     */
    public void removeContribution(long contributionId) {
        if (!this.contributionRepository.existsById(contributionId)) {
            throw new IllegalArgumentException("Contribution does not exist");
        }
        this.contributionRepository.deleteById(contributionId);
    }
}
