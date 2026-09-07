package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "pantry_staples",
        uniqueConstraints = @UniqueConstraint(
                name = "fk_user_pantrystaples",
                columnNames = {"user_id"}
        )
)
public class PantryStaplesJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpa userJpa;

    @Column(name = "is_default")
    private boolean isDefault;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "pantryStaplesJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PantryStaplesLineJpa> pantryStaplesLineSet = new HashSet<>();

    public PantryStaplesJpa() {
    }

    public PantryStaplesJpa(UserJpa userJpa, boolean isDefault) {
        this.userJpa = userJpa;
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public UserJpa getUserJpa() {
        return userJpa;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Set<PantryStaplesLineJpa> getPantryStaplesLineSet() {
        return Set.copyOf(pantryStaplesLineSet);
    }
}
