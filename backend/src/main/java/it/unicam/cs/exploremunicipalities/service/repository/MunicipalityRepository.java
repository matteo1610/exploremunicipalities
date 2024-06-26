package it.unicam.cs.exploremunicipalities.service.repository;

import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends CrudRepository<Municipality, Long> {
    @Query("SELECT m FROM Municipality m WHERE m.name = ?1 AND m.province = ?2")
    Municipality findByNameAndProvince(String name, String province);
}
