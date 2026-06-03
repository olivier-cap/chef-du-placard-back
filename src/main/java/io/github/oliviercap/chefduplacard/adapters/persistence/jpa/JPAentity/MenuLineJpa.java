package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.math.BigDecimal;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recette_id", nullable = false)
    private RecipeJpa recipeJpa;

    @Column(name = "nombre_personnes", nullable = false, precision = 10, scale = 2)
    private BigDecimal nbPerson;

    public MenuLineJpa() {
    }

    public MenuLineJpa(MenuJpa menuJpa,
                       RecipeJpa recipeJpa,
                       BigDecimal nbPerson) {
        this.menuJpa = menuJpa;
        this.recipeJpa = recipeJpa;
        this.nbPerson = nbPerson;
    }

    public MenuLineJpa(RecipeJpa recipeJpa,
                       BigDecimal nbPerson) {
        this.recipeJpa = recipeJpa;
        this.nbPerson = nbPerson;
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

    public RecipeJpa getRecipeJpa() {
        return recipeJpa;
    }

    public void setRecipeJpa(RecipeJpa recipeJpa) {
        this.recipeJpa = recipeJpa;
    }

    public BigDecimal getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(BigDecimal quantityPerPerson) {
        this.nbPerson = quantityPerPerson;
    }
}
