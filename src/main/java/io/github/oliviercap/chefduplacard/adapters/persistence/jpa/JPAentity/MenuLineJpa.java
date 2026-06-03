package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "menu_line"
)
public class MenuLineJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuJpa menuJpa;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RecipeJpa> recipeJpaList = new ArrayList<>();

    @Column(name = "nombre_personnes", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantityPerPerson;

    public MenuLineJpa() {
    }

    public MenuLineJpa(MenuJpa menuJpa,
                       List<RecipeJpa> recipeJpaList,
                       BigDecimal quantityPerPerson) {
        this.menuJpa = menuJpa;
        this.recipeJpaList = recipeJpaList;
        this.quantityPerPerson = quantityPerPerson;
    }

    public MenuLineJpa(List<RecipeJpa> recipeJpaList,
                       BigDecimal quantityPerPerson) {
        this.recipeJpaList = recipeJpaList;
        this.quantityPerPerson = quantityPerPerson;
    }

    public Long getId() {
        return id;
    }

    public MenuJpa getMenuJpa() {
        return menuJpa;
    }

    public void setMenuJpa(MenuJpa menuJpa) {
        this.menuJpa = menuJpa;
    }

    public List<RecipeJpa> getRecipeJpaList() {
        return recipeJpaList;
    }

    public void setRecipeJpaList(List<RecipeJpa> recipeJpaList) {
        this.recipeJpaList = recipeJpaList;
    }

    public BigDecimal getQuantityPerPerson() {
        return quantityPerPerson;
    }

    public void setQuantityPerPerson(BigDecimal quantityPerPerson) {
        this.quantityPerPerson = quantityPerPerson;
    }
}
