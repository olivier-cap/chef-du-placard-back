package io.github.oliviercap.chefduplacard.domain.unit;

//Penser à faire qqchose comme une factory, un visitor ou autre
//faire une interface unité, puis plein d'unités différentes :)

public class Unit {
    private String name;
    private String symbol;

    public Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }


}
