package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA entity.Represents an Aliment.
 * Used to read / write data in database.
 */
@Entity
@Table(
        name = "aliment",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class AlimentJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "actif", nullable = false)
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "aliment_type_join",
            joinColumns = @JoinColumn(name = "aliment_id"),
            inverseJoinColumns = @JoinColumn(name = "aliment_type_id")
    )
    private Set<AlimentTypeJpa> alimentTypes = new HashSet<>();

    protected AlimentJpa() {
    }

    public AlimentJpa(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }
    public AlimentJpa(
            Long id,
            String name,
            String description,
            boolean active
    ) {
        this.id = id;
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

    public Set<AlimentTypeJpa> getAlimentTypes() {
        return Set.copyOf(alimentTypes);
    }

}
