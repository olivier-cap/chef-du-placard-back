package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
    name="menu"
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
    private List<MenuLineJpa> menuLineJpaList = new ArrayList<>();

    @Column(name = "nom", nullable = false, unique = true)
    private String name;

    public MenuJpa() {
    }

    public MenuJpa(List<MenuLineJpa> menuLineJpaList, String name) {
        this.menuLineJpaList = menuLineJpaList;
        this.name = name;
    }

    public void addMenuLine(MenuLineJpa menuLineJpa) {
        Objects.requireNonNull(menuLineJpa, "menuLineJpa must not be null");
        menuLineJpaList.add(menuLineJpa);
        menuLineJpa.setMenuJpa(this);
    }

    public Long getId() {
        return id;
    }

    public List<MenuLineJpa> getMenuLineJpaList() {
        return menuLineJpaList;
    }

    public void setMenuLineJpaList(List<MenuLineJpa> menuLineJpaList) {
        this.menuLineJpaList = menuLineJpaList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
