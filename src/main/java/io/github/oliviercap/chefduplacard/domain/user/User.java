package io.github.oliviercap.chefduplacard.domain.user;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.Objects;

public class User {

    private final UserId userId;
    private String pseudonym;
    private String email;
    private boolean isAdmin;

    public User(UserId userId, String pseudonym, String email, boolean isAdmin) {
        if (userId == null) {
            throw new DomainException("User id must not be null");
        }
        if (pseudonym == null || pseudonym.isBlank()) {
            throw new DomainException("User pseudonym must not be null");
        }
        if (email == null || email.isBlank()) {
            throw new DomainException("User email must not be null");
        }

        this.userId = userId;
        this.pseudonym = pseudonym;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return isAdmin == user.isAdmin && Objects.equals(userId, user.userId) && Objects.equals(pseudonym, user.pseudonym) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, pseudonym, email, isAdmin);
    }
}
