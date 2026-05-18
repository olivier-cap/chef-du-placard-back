package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An Ingredient is an aliment associated with its quantity per person in a recipe.
 */
public final class Ingredient {
    private BigDecimal quantity;
    private final Aliment aliment;
    private final Unit unit;

    public Ingredient(BigDecimal quantity, Aliment aliment, Unit unit) {
        if (quantity == null) {
            throw new DomainException("Quantity per person must not be null");
        }
        if (quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Quantity must be greater than or equal to 0");
        }
        if(aliment == null) {
            throw new DomainException("Ingredient must have an aliment, aliment cannot be null");
        }
        if(unit == null) {
            throw new DomainException("Ingredient must have a unit, unit cannot be null");
        }
        this.quantity = quantity;
        this.aliment = aliment;
        this.unit = unit;
    }

    /** Getters and Setters **/

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Aliment getAliment() {
        return aliment;
    }

    public Unit getUnit() {
        return unit;
    }

    private void setQuantity(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("quantityPerPerson must not be less than zero or null");
        }

        this.quantity = quantity;
    }

    public void addQuantityFrom(Ingredient other) {
        if (other == null) {
            throw new DomainException("ingredient to add must not be null");
        }

        if (!this.aliment.equals(other.aliment)) {
            throw new DomainException("cannot add quantities from different aliments");
        }

        if (!this.unit.equals(other.unit)) {
            throw new DomainException("cannot add quantities with different units in V1");
        }

        setQuantity(this.quantity.add(other.quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient that)) return false;
        return Objects.equals(quantity, that.quantity) && Objects.equals(aliment, that.aliment) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, aliment, unit);
    }
}
