package it.unicam.cs.exploremunicipalities.service.repository;

import it.unicam.cs.exploremunicipalities.model.user.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long>{
}
