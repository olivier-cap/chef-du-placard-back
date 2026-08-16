package io.github.oliviercap.chefduplacard.domain.unit;

//Penser à faire qqchose comme une factory, un visitor ou autre
//faire une interface unité, puis plein d'unités différentes :)

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.Objects;

/**
 * A Unit represents a unit and its symbol used to quantify an aliment.
 */
public class Unit {
    private final UnitId id;
    private String name;
    private String symbol;

    public Unit(UnitId id,
                String name,
                String symbol
    ) {
        if(id == null) {
            throw new DomainException("Unit id must not be null");
        }
        if(name == null || name.isBlank()) {
            throw new DomainException("unit name must not be empty, blank or null");
        }
        if(symbol == null || symbol.isBlank()) {
            throw new DomainException("unit symbol must not bet empty, blank or null");
        }
        this.name = name;
        this.symbol = symbol;
        this.id = id;
    }

    /**
     * Check this unit
     * @return true if this unit is correctly formed
     */
    public boolean check() {
        return name != null && !name.isBlank() && symbol != null && !symbol.isBlank();
    }

    /** Getters and Setters **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public UnitId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Unit unit)) return false;
        return Objects.equals(id, unit.id) && Objects.equals(name, unit.name) && Objects.equals(symbol, unit.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol);
    }
}
