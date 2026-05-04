package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

@Entity
@Table(name = "aliment")
public class AlimentJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "actif")
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
