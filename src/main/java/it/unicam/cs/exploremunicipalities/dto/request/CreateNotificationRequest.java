package it.unicam.cs.exploremunicipalities.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateNotificationRequest(
        @NotNull(message = "The message cannot be null")
        String message
){
}
