package io.github.oliviercap.chefduplacard.domain.stock.virtualstock;

import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.Objects;

/*
Génération d'un stock virtuel, c.a.d la copie conforme du stock actuel
 */
public class VirtualStockFactory {

    public VirtualStock createForMenuPreparation(Stock stock) {
        Objects.requireNonNull(stock, "stock must not be null");

        Stock stockCopy = stock.copyForSimulation(stock.getName() + " - virtual");
        return new VirtualStock(stockCopy);
    }
}
