package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.ContributionRepository;
import it.unicam.cs.exploremunicipalities.controller.repository.PointRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.ContributionServiceInterface;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.service.OSMProxy;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
import it.unicam.cs.exploremunicipalities.model.service.OSMServiceInterface;
import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ContributionService implements ContributionServiceInterface {
    private final ContributionRepository contributionRepository;
    private final PointRepository pointRepository;
    private final OSMServiceInterface osmService;

    public ContributionService(ContributionRepository contributionRepository, PointRepository pointRepository) {
        this.contributionRepository = contributionRepository;
        this.pointRepository = pointRepository;
        this.osmService = new OSMProxy(new OSMService());
    }

    /**
     * Returns a point with the given id.
     * @param pointId the id of the point to get
     * @return a point with the given id
     * @throws IllegalArgumentException if the point does not exist
     */
    public Point getPoint(long pointId) {
        return this.pointRepository.findById(pointId).orElseThrow();
    }

    /**
     * Returns all the point of the municipality.
     * @param municipality the municipality to get the points of
     * @return all the points of the municipality
     */
    public Set<Point> getAllPoints(Municipality municipality) {
        return this.pointRepository.findByMunicipality(municipality.getId());
    }

    /**
     * Returns all contributions associated with the given point.
     * @param pointId the id of the point
     * @return all contributions associated with the given point
     */
    public Set<Contribution> getContributionsOfPoint(long pointId) {
        return this.getPoint(pointId).getContributions();
    }

    /**
     * Returns a contribution with the given id.
     * @param contributionId the id of the contribution to get
     * @return a contribution with the given id
     * @throws IllegalArgumentException if the contribution does not exist
     */
    public Contribution getContribution(long contributionId) {
        return this.contributionRepository.findById(contributionId).orElseThrow();
    }

    /**
     * Adds a contribution to the point.
     * @param position the position of the point
     * @param municipality the municipality of the point
     * @param contribution the contribution to add
     * @throws IllegalArgumentException if the point is not in the municipality
     * @throws Exception if an error occurs while getting information from the OSM service
     */
    public void addContributionToPoint(CoordinatePoint position, Municipality municipality, Contribution contribution)
            throws Exception {
        Point p = this.pointRepository.findByCoordinateAndMunicipality(position, municipality.getId());
        if (p == null) {
            if (this.osmService.isPointInMunicipality(position, municipality)) {
                p = new Point(position, municipality);
                this.pointRepository.save(p);
            } else {
                throw new IllegalArgumentException("The point is not in the municipality");
            }
        }
        p.addContribution(contribution);
        this.contributionRepository.save(contribution);
    }

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

    /**
     * Removes a point in the repository. All contributions associated with the point are removed as well.
     * @param pointId the id of the point to remove
     * @throws IllegalArgumentException if the point does not exist
     */
    public void removePointById(long pointId) {
        if (!this.pointRepository.existsById(pointId)) {
            throw new IllegalArgumentException("Point does not exist");
        }
        this.pointRepository.deleteById(pointId);
    }

    /**
     * Removes all points of the municipality. All contributions associated with the points are removed as well.
     * @param municipality the municipality to remove the points of
     */
    public void removeAllPointOfMunicipality(Municipality municipality) {
        this.getAllPoints(municipality).forEach(p -> this.removePointById(p.getId()));
    }
}
