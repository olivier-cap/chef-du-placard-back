package io.github.oliviercap.chefduplacard.domain.pantry_staples;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

public class PantryStaplesLine {

    private final PantryStaplesLineId id;
    private Aliment aliment;
    private Unit unit;
    private BigDecimal quantity;

    public PantryStaplesLine(PantryStaplesLineId id, Aliment aliment, Unit unit, BigDecimal quantity) {
        this.id = id;
        this.aliment = aliment;
        this.unit = unit;
        this.quantity = quantity;
    }

    public PantryStaplesLineId getId() {
        return id;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PantryStaplesLine that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(aliment, that.aliment) && Objects.equals(unit, that.unit) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aliment, unit, quantity);
    }
}
