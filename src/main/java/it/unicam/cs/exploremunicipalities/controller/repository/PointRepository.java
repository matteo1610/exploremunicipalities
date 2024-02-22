package it.unicam.cs.exploremunicipalities.controller.repository;

import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PointRepository extends CrudRepository<Point, Long> {
    @Query("SELECT p FROM Point p WHERE p.position = ?1 AND p.municipality.id = ?2")
    Point findByCoordinateAndMunicipality(CoordinatePoint position, long municipalityId);
}
