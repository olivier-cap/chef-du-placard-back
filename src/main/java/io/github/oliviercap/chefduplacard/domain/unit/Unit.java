package io.github.oliviercap.chefduplacard.domain.unit;

//Penser à faire qqchose comme une factory, un visitor ou autre
//faire une interface unité, puis plein d'unités différentes :)

/**
 * A Unit represents a unit and its symbol used to quantify an aliment.
 */
public class Unit {
    private String name;
    private String symbol;

    public Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
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
}
