package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.controller.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a user with an email and a password.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "AUTH_USER")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private boolean isAdmin;
    @Setter
    @OneToOne(cascade = CascadeType.REMOVE)
    private License license;

    /**
     * Creates a new user with the given email and password.
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Converts the user to a DTO.
     * @return the DTO of the user
     */
    public UserDTO toDTO() {
        return new UserDTO(this.id, this.email, this.password, license.toDTO());
    }
}
