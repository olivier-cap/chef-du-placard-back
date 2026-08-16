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

    private final StockLineId id;
    private BigDecimal quantity;
    private final Aliment aliment;
    private final Unit unit;

    public StockLine(StockLineId id,
                     BigDecimal quantity,
                     Aliment aliment,
                     Unit unit
    ) {
        if(id == null) {
            throw new DomainException("Stock line id must not null");
        }
        if(quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new DomainException("Stock line quantity must not be less than zero or null.");
        }

        if(aliment == null) {
            throw new DomainException("stock line aliment must not be null");
        }

        if(unit == null) {
            throw new DomainException("stock line unit must not be null");
        }

        this.id = id;
        this.quantity = quantity;
        this.aliment = aliment;
        this.unit = unit;
    }

    /**
    Create a copy of this stockLine for virtual stock.
    Only the quantity will be new, same aliment and same unit are used
    */
    StockLine copyForSimulation() {
        return new StockLine(
                this.getId(),
                this.getQuantity(),
                this.getAliment(),
                this.getUnit()
        );
    }

    /**
     * Check is this stockline is complete and correct
     * @return true if stockline is correctly formed
     */
    public boolean check() {
        return this.id != null && this.quantity != null && this.aliment.check() && this.unit.check();
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
            throw new DomainException("stock line quantity must not be less than zero or null");
        }

        this.quantity = quantity;
    }

    public StockLineId getId() {
        return id;
    }

    /**
     * Subtract quantity of an aliment by quantity in "other"
     * If result is less than 0, quantity is defined to 0
     */
    public void subtractQuantity(
            Aliment otherAliment,
            Unit otherUnit,
            BigDecimal otherQuantity
    ) {
        if (otherAliment == null || otherQuantity == null || otherUnit == null) {
            throw new DomainException("ingredient to add must not be null");
        }

        if (!this.aliment.equals(otherAliment)) {
            throw new DomainException("cannot add quantities from different aliments");
        }

        if (!this.unit.equals(otherUnit)) {
            throw new DomainException("cannot add quantities with different units in V1");
        }

        BigDecimal newQuantity = this.quantity.subtract(otherQuantity);
        if(newQuantity.compareTo(BigDecimal.ZERO) < 0) {
            setQuantity(BigDecimal.ZERO);
        }
        else {
            setQuantity(newQuantity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockLine stockLine)) return false;
        return Objects.equals(id, stockLine.id) && Objects.equals(quantity, stockLine.quantity) && Objects.equals(aliment, stockLine.aliment) && Objects.equals(unit, stockLine.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, aliment, unit);
    }
}
