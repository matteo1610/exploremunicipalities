package it.unicam.cs.exploremunicipalities.dto.request;

public record SetLicenseRequest(
        long userId,
        long municipalityId,
        String role
) { }
