package io.github.oliviercap.chefduplacard.domain.unit;

//Penser à faire qqchose comme une factory, un visitor ou autre
//faire une interface unité, puis plein d'unités différentes :)

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.Objects;

/**
 * A Unit represents a unit and its symbol used to quantify an aliment.
 */
public class Unit {
    private String name;
    private String symbol;

    public Unit(String name, String symbol) {
        if(name.isBlank() || name == null) {
            throw new DomainException("unit name must not be empty, blank or null");
        }
        if(symbol.isBlank() || symbol == null) {
            throw new DomainException("unit symbol must not bet empty, blank or null");
        }
        this.name = name;
        this.symbol = symbol;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Unit unit)) return false;
        return Objects.equals(name, unit.name) && Objects.equals(symbol, unit.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, symbol);
    }
}
