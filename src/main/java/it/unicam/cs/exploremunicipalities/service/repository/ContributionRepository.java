package it.unicam.cs.exploremunicipalities.service.repository;

import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends CrudRepository<Contribution, Long> {
}
