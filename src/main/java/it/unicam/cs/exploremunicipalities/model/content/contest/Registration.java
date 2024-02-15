package it.unicam.cs.exploremunicipalities.model.content.contest;

import it.unicam.cs.exploremunicipalities.model.user.User;

import java.io.File;
import java.util.UUID;

/**
 * A participation to a contest.
 */
public class Registration {
    private final UUID id;
    private final User user;
    private Contest contest;
    private File file;

    /**
     * Creates a new participation.
     *
     * @param user the user that participates
     * @param contest the contest to participate to
     * @param file the file of the participation
     */
    public Registration(User user, Contest contest, File file) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.contest = contest;
        this.file = file;
    }

    public UUID getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
