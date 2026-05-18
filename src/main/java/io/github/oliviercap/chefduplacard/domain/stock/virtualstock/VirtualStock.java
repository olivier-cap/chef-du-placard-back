package io.github.oliviercap.chefduplacard.domain.stock.virtualstock;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.stock.CoveredIngredients;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.List;
import java.util.Objects;

/*
Stock virtuel, c.a.d la copie conforme du stock actuel,
dont on peut modifier les quantités par aliment sans modifier le stock réel.
Permet de générer des listes de recettes réalisables sans modifier le stock lors des calculs.
 */
public class VirtualStock {
    private Stock stockCopy;

    public VirtualStock(Stock stockCopy) {
        Objects.requireNonNull(stockCopy, "stockCopy must not be null");
        this.stockCopy = stockCopy;
    }

    public CoveredIngredients covers(List<Ingredient> requiredIngredients) {
        return stockCopy.covers(requiredIngredients);
    }

    public boolean consume(List<Ingredient> ingredients){
        return stockCopy.consume(ingredients);
    }

}