package it.unicam.cs.exploremunicipalities.dto.request;

public record AuthenticateRequest(
        String email,
        String password
) { }
