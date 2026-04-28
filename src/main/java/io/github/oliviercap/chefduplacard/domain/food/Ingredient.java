package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;

/**
 * An Ingredient is an aliment associated with its quantity per person in a recipe.
 */
public class Ingredient {
    private BigDecimal quantityPerPerson;
    private Aliment aliment;
    private Unit unit;

    public Ingredient(BigDecimal quantityPerPerson, Aliment aliment, Unit unit) {
        this.quantityPerPerson = quantityPerPerson;
        this.aliment = aliment;
        this.unit = unit;
    }

    /** Getters and Setters **/

    public BigDecimal getQuantityPerPerson() {
        return quantityPerPerson;
    }

    public void setQuantityPerPerson(BigDecimal quantityPerPerson) {
        this.quantityPerPerson = quantityPerPerson;
    }

    public Aliment getAliment() {
        return aliment;
    }

    public void setAliment(Aliment aliment) {
        this.aliment = aliment;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
