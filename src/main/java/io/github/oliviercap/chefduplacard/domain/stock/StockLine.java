package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Stock Line is a part of a Stock.
 * A Stock Line represents the quantity actually presents in stock of an aliment.
 */
public final class StockLine {

    private final BigDecimal quantity;
    private final Aliment aliment;
    private final Unit unit;

    public StockLine(BigDecimal quantity, Aliment aliment, Unit unit) {
        if(quantity.compareTo(BigDecimal.ZERO) < 0 || quantity == null) {
            throw new DomainException("Ingredient quantity must not be less than zero or null.");
        }

        if(aliment == null) {
            throw new DomainException("Ingredient's aliment must not be null");
        }

        if(unit == null) {
            throw new DomainException("Ingredient's unit must not be null");
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockLine stockLine)) return false;
        return Objects.equals(quantity, stockLine.quantity) && Objects.equals(aliment, stockLine.aliment) && Objects.equals(unit, stockLine.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, aliment, unit);
    }
}
