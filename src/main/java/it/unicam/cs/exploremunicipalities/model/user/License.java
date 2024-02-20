package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a license with a user, a municipality and a role.
 */
@Getter
@NoArgsConstructor
@Entity
public class License {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User user;
    @Setter
    @ManyToOne
    private Municipality municipality;
    @Setter
    private UserRole role;

    /**
     * Creates a new license with the given user, municipality and role.
     * @param user the user of the license
     * @param municipality the municipality of the license
     * @param role the role of the license
     */
    public License(User user, Municipality municipality, UserRole role) {
        this.user = user;
        this.municipality = municipality;
        this.role = role;
    }
}
