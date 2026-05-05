package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

/**
 * JPA entity.Represents an Aliment.
 * Used to read / write data in database.
 */
@Entity
@Table(
        name = "aliment",
        uniqueConstraints = @UniqueConstraint(columnNames = "nom")

)
public class AlimentJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 150)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "actif", nullable = false)
    private boolean active;

    protected AlimentJpa() {
    }

    public AlimentJpa(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
