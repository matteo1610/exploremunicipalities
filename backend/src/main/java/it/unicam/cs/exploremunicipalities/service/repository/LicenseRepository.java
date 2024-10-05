package it.unicam.cs.exploremunicipalities.service.repository;

import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.MunicipalityRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LicenseRepository extends CrudRepository<License, Long> {
    @Query("SELECT l.role FROM License l WHERE l.municipality.id = ?1")
    Set<MunicipalityRole> findRolesByMunicipality(long municipalityId);
}
