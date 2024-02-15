package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.model.content.contest.Contest;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is responsible for managing the platform.
 */
public class PlatformService {
    private final UserService userService;
    private final MunicipalityService municipalityService;

    private static final Map<UUID, User> userRepository = new HashMap<>();
    {
        User user1 = new User("user1@email.com", "password1");
        userRepository.put(user1.getId(), user1);
        User user2 = new User("user2@email.com", "password2");
        userRepository.put(user2.getId(), user2);
    }
    private static final Map<UUID, Municipality> municipalityRepository = new HashMap<>();
    {
        Municipality municipality1 = new Municipality("Camerino", "MC", new CoordinatePoint(
                43.1351, 13.0688));
        municipalityRepository.put(municipality1.getId(), municipality1);
        Municipality municipality2 = new Municipality("Civitanova", "MC", new CoordinatePoint(
                43.3096, 13.6746));
        municipalityRepository.put(municipality2.getId(), municipality2);
        Municipality municipality3 = new Municipality("Osimo", "AN", new CoordinatePoint(
                43.4878, 13.4878));
        municipalityRepository.put(municipality3.getId(), municipality3);
    }
    private static final Map<UUID, Point> pointRepository = new HashMap<>();
    private static final Map<UUID, Contribution> contributionRepository= new HashMap<>();
    private static final Map<UUID, Contest> contestRepository = new HashMap<>();

    public PlatformService() {
        this.userService = new UserService(userRepository);
        this.municipalityService = new MunicipalityService(municipalityRepository, pointRepository,
                contributionRepository, contestRepository);
    }

    public UserService getUserService() {
        return this.userService;
    }

    public MunicipalityService getMunicipalityService() {
        return this.municipalityService;
    }
}
