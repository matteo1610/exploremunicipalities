package it.unicam.cs.exploremunicipalities.dto.response;

import it.unicam.cs.exploremunicipalities.dto.entities.UserDTO;

public record RegisterResponse(
        UserDTO user
) { }
