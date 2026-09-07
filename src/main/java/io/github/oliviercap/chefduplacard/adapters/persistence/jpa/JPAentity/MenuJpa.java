package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

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

    @Column(name = "name")
    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "menuJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MenuLineJpa> menuLineJpaList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private UserJpa userJpa;

    public MenuJpa() {
    }

    public MenuJpa(List<MenuLineJpa> menuLineJpaList, String name) {
        this.menuLineJpaList = menuLineJpaList;
        this.name = name;
    }

    public MenuJpa(String name, List<MenuLineJpa> menuLineJpaList, UserJpa userJpa) {
        this.name = name;
        this.menuLineJpaList = menuLineJpaList;
        this.userJpa = userJpa;
    }

    public void addMenuLine(MenuLineJpa menuLineJpa) {
        Objects.requireNonNull(menuLineJpa, "menuLineJpa must not be null");
        menuLineJpaList.add(menuLineJpa);
        menuLineJpa.setMenuJpa(this);
    }

    public UserJpa getUserJpa() {
        return userJpa;
    }

    public void setUserJpa(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    public Long getId() {
        return id;
    }

    public List<MenuLineJpa> getMenuLineJpaList() {
        return List.copyOf(menuLineJpaList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
