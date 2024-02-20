package it.unicam.cs.exploremunicipalities.controller.repository;

import it.unicam.cs.exploremunicipalities.model.user.License;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LicenseRepository extends CrudRepository<License, Long> {
    @Query("SELECT l FROM License l WHERE l.user.id = ?1 AND l.municipality.id = ?2")
    License findByUserIdAndMunicipalityId(Long userId, Long municipalityId);
    @Query("SELECT l.municipality FROM License l WHERE l.user.id = ?1")
    Set<License> findByUserId(Long userId);
}
