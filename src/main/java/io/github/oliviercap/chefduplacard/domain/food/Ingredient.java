package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient that)) return false;
        return Objects.equals(quantityPerPerson, that.quantityPerPerson) && Objects.equals(aliment, that.aliment) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantityPerPerson, aliment, unit);
    }
}
