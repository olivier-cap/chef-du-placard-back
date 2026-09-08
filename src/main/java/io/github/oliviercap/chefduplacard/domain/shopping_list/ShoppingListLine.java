package io.github.oliviercap.chefduplacard.domain.shopping_list;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.math.BigDecimal;
import java.util.Objects;

public class ShoppingListLine {

    private final ShoppingListLineId id;
    private Aliment aliment;
    private Unit unit;
    private BigDecimal quantity;

    public ShoppingListLine(ShoppingListLineId id, Aliment aliment, Unit unit, BigDecimal quantity) {
        if (id == null) {
            throw new DomainException("shopping list id must not be null");
        }
        if (aliment == null) {
            throw new DomainException("aliment must not be null");
        }
        if (unit == null) {
            throw new DomainException("unit must not be null");
        }
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("quantity must be positive and non null");
        }
        this.id = id;
        this.aliment = aliment;
        this.unit = unit;
        this.quantity = quantity;
    }

    public ShoppingListLineId getId() {
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
        if (!(o instanceof ShoppingListLine that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(aliment, that.aliment) && Objects.equals(unit, that.unit) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aliment, unit, quantity);
    }
}
