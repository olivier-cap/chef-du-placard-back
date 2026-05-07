package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An Ingredient is an aliment associated with its quantity per person in a recipe.
 */
public final class Ingredient {
    private final BigDecimal quantityPerPerson;
    private final Aliment aliment;
    private final Unit unit;

    public Ingredient(BigDecimal quantityPerPerson, Aliment aliment, Unit unit) {
        if (quantityPerPerson == null) {
            throw new DomainException("Quantity per person must not be null");
        }
        if (quantityPerPerson.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Quantity must be greater than or equal to 0");
        }
        if(aliment == null) {
            throw new DomainException("Ingredient must have an aliment, aliment cannot be null");
        }
        if(unit == null) {
            throw new DomainException("Ingredient must have a unit, unit cannot be null");
        }
        this.quantityPerPerson = quantityPerPerson;
        this.aliment = aliment;
        this.unit = unit;
    }

    /** Getters and Setters **/

    public BigDecimal getQuantityPerPerson() {
        return quantityPerPerson;
    }

    public Aliment getAliment() {
        return aliment;
    }

    public Unit getUnit() {
        return unit;
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
