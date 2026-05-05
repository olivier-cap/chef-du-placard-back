package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Stock Line is a part of a Stock.
 * A Stock Line represents the quantity actually presents in stock of an aliment.
 */
public class StockLine {

    private BigDecimal quantity;
    private Aliment aliment;
    private Unit unit;

    public StockLine(BigDecimal quantity, Aliment aliment, Unit unit) {
        this.quantity = quantity;
        this.aliment = aliment;
        this.unit = unit;
    }

    /** Getters and Setters **/

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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
        if (!(o instanceof StockLine stockLine)) return false;
        return Objects.equals(quantity, stockLine.quantity) && Objects.equals(aliment, stockLine.aliment) && Objects.equals(unit, stockLine.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, aliment, unit);
    }
}
