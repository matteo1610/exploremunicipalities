package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.dto.PointDTO;
import it.unicam.cs.exploremunicipalities.controller.repository.PointRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.PointServiceInterface;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.service.OSMProxy;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
import it.unicam.cs.exploremunicipalities.model.service.OSMServiceInterface;
import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PointService implements PointServiceInterface {
    private final PointRepository pointRepository;
    private final MunicipalityService municipalityService;
    private final OSMServiceInterface osmService;

    public PointService(PointRepository pointRepository, MunicipalityService municipalityService) {
        this.pointRepository = pointRepository;
        this.municipalityService = municipalityService;
        this.osmService = new OSMProxy(new OSMService());
    }

    /**
     * Returns the details of the points associated to the municipality with the given id.
     *
     * @return the details of the points associated to the municipality with the given id
     */
    public Set<PointDTO> getPoints(long municipalityId) {
        Set<PointDTO> points = new HashSet<>();
        for (Point point : this.municipalityService.getMunicipality(municipalityId).getPoints()) {
            points.add(point.toDTO());
        }
        return points;
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











    private Point checkPoint(CoordinatePoint position, Municipality municipality) throws Exception {
        Point p = this.pointRepository.findByCoordinateAndMunicipality(position, municipality.getId());
        if (p == null) {
            if (this.osmService.isPointInMunicipality(position, municipality)) {
                p = this.pointRepository.save(new Point(position, municipality));
            } else {
                throw new IllegalArgumentException("The point is not in the municipality");
            }
        }
        return p;
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
}
