package it.unicam.cs.exploremunicipalities.controller.repository;

import it.unicam.cs.exploremunicipalities.model.content.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends CrudRepository<Point, String>{
}
