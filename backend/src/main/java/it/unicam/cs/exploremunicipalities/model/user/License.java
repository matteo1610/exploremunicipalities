package it.unicam.cs.exploremunicipalities.model.user;

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
    private MunicipalityRole role;
    @Setter
    @ManyToOne
    private Municipality municipality;

    /**
     * Constructs a license with the given role and municipality.
     *
     * @param role         The role of the user.
     * @param municipality The municipality the user is associated with. If this is null, th.
     */
    public License(MunicipalityRole role, Municipality municipality) {
        this.role = role;
        this.municipality = municipality;
    }
}
