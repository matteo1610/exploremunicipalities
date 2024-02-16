package it.unicam.cs.exploremunicipalities.controller.repository;

import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends CrudRepository<Municipality, Long>{
}
