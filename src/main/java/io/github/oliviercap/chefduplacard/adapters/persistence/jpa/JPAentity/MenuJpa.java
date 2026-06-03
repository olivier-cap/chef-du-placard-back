package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(

)
public class MenuJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "menuJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MenuLineJpa> menuLineJpaList;
}
