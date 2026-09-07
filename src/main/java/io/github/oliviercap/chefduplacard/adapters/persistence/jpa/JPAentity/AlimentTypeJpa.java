package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "aliment_type"
)
public class AlimentTypeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "alimentTypes", fetch = FetchType.LAZY)
    private Set<AlimentJpa> alimentJpaSet = new HashSet<>();

    public AlimentTypeJpa() {
    }

    public AlimentTypeJpa(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Set<AlimentJpa> getAlimentJpaSet() {
        return Set.copyOf(alimentJpaSet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
