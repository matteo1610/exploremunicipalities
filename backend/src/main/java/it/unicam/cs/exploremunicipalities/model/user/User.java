package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private GlobalRole role;
    @Setter
    @OneToOne(cascade = CascadeType.REMOVE)
    private License license;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Notification> notifications;
    @OneToMany
    private Set<Contribution> favorites;

    /**
     * Creates a new user with the given email and password.
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = GlobalRole.AUTHENTICATED_TOURIST;
        this.license = null;
        this.notifications = new HashSet<>();
        this.favorites = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        if (this.role == GlobalRole.MUNICIPALITY_USER && this.license != null) {
            authorities.add(new SimpleGrantedAuthority(this.license.getRole().name()));
        }
        return authorities;
         */
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void addFavorite(Contribution contribution) {
        if (contribution.getState() != ContributionState.APPROVED) {
            throw new IllegalArgumentException("You can save only approved contributions as favorites.");
        }
        this.favorites.add(contribution);
    }

    public void removeFavorite(Contribution contribution) {
        this.favorites.remove(contribution);
    }
}
