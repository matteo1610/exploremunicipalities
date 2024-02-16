package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.*;
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
    private final RoleService roleService;
    private final UserService userService;
    private final MunicipalityService municipalityService;

    public PlatformService(LicenseRepository licenseRepository, UserRepository userRepository,
                           MunicipalityRepository municipalityRepository, PointRepository pointRepository,
                           ContributionRepository contributionRepository) {
        this.municipalityService = new MunicipalityService(municipalityRepository, pointRepository,
                contributionRepository);
        this.roleService = new RoleService(licenseRepository);
        this.userService = new UserService(userRepository, this.municipalityService, this.roleService);
    }
}
