package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "users"
)
public class UserJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="pseudonym", nullable = false, unique = true)
    private String pseudo;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name = "is_admin")
    private boolean isAdmin;

    public UserJpa() {
    }

    public UserJpa(String pseudo, String email, boolean isAdmin) {
        this.pseudo = pseudo;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public UserJpa(
            Long id,
            String pseudo,
            String email,
            boolean isAdmin
    ) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.isAdmin = isAdmin;
    }
    public Long getId() {
        return id;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
