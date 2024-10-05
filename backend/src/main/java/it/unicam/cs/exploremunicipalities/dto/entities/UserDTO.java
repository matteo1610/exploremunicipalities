package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.user.User;
import lombok.Getter;

@Getter
public class UserDTO {
    private final long id;
    private final String email;
    private final String role;
    private final LicenseDTO license;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.license = user.getLicense() != null ? new LicenseDTO(user.getLicense()) : null;
    }
}