package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.PointDTO;
import it.unicam.cs.exploremunicipalities.service.repository.PointRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.PointServiceInterface;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.service.OSMProxy;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
import it.unicam.cs.exploremunicipalities.model.service.OSMServiceInterface;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
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

    @Override
    public Set<PointDTO> getPoints(long municipalityId) {
        Set<PointDTO> points = new HashSet<>();
        for (Point point : this.municipalityService.getMunicipality(municipalityId).getPoints()) {
            points.add(point.toDTO());
        }
        return points;
    }

    @Override
    public Point getPoint(long pointId) {
        return this.pointRepository.findById(pointId).orElseThrow();
    }

    @Override
    public Point associatePoint(Coordinate position, long municipalityId) throws Exception {
        Municipality municipality = this.municipalityService.getMunicipality(municipalityId);
        Point point = municipality.getPoints().stream().filter(p -> p.getPosition().equals(position)).findFirst()
                .orElse(null);
        if (point == null) {
            point = this.createPoint(position, municipality);
        }
        return point;
    }

    private Point createPoint(Coordinate position, Municipality municipality) throws Exception {
        if (!this.osmService.isPointInMunicipality(position, municipality)) {
            throw new IllegalArgumentException("The point is not in the municipality");
        }
        Point p = new Point(position, municipality);
        municipality.addPoint(p);
        return this.pointRepository.save(p);
    }

    @Override
    public void deletePoint(long pointId) {
        this.pointRepository.delete(this.getPoint(pointId));
    }
}
