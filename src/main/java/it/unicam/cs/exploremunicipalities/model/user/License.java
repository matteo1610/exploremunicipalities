package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.dto.LicenseDTO;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a license to access the system for a specific role in a specific municipality.
 */
@Getter
@NoArgsConstructor
@Entity
public class License {
    @Id
    @GeneratedValue
    private long id;
    @Setter
    private UserRole role;
    @Setter
    @ManyToOne
    private Municipality municipality;

    /**
     * Constructs a license with the given role and municipality.
     *
     * @param role         The role of the user.
     * @param municipality The municipality the user is associated with.
     */
    public License(UserRole role, Municipality municipality) {
        this.role = role;
        this.municipality = municipality;
    }

    /**
     * Converts the license to a DTO.
     *
     * @return The DTO representing the license.
     */
    public LicenseDTO toDTO() {
        return new LicenseDTO(this.role, this.municipality.getId());
    }
}
