package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "ingredient",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_ingredient_recette_aliment",
                columnNames = {"recette_id", "aliment_id"}
        )
)
public class IngredientJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recette_id", nullable = false)
    private RecipeJpa recipeJpa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aliment_id", nullable = false)
    private AlimentJpa alimentJpa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unite_id", nullable = false)
    private UnitJpa unitJpa;

    @Column(name = "quantite_par_personne", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantityPerPerson;

    protected IngredientJpa() {
    }

    public IngredientJpa(
            RecipeJpa recipeJpa,
            AlimentJpa alimentJpa,
            UnitJpa unitJpa,
            BigDecimal quantityPerPerson
    ) {
        this.recipeJpa = recipeJpa;
        this.alimentJpa = alimentJpa;
        this.unitJpa = unitJpa;
        this.quantityPerPerson = quantityPerPerson;
    }

    public Long getId() {
        return id;
    }

    public RecipeJpa getRecipeJpa() {
        return recipeJpa;
    }

    public void setRecipeJpa(RecipeJpa recipeJpa) {
        this.recipeJpa = recipeJpa;
    }

    public AlimentJpa getAlimentJpa() {
        return alimentJpa;
    }

    public void setAlimentJpa(AlimentJpa alimentJpa) {
        this.alimentJpa = alimentJpa;
    }

    public UnitJpa getUnitJpa() {
        return unitJpa;
    }

    public void setUnitJpa(UnitJpa unitJpa) {
        this.unitJpa = unitJpa;
    }

    public BigDecimal getQuantityPerPerson() {
        return quantityPerPerson;
    }

    public void setQuantityPerPerson(BigDecimal quantityPerPerson) {
        this.quantityPerPerson = quantityPerPerson;
    }
}