package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stock d'aliments.
 * Le stock est représenté par un ensemble de lignes aliment <-> quantité.
 */
public class Stock {
    //Pour permettre recherches par aliment, stockage des données dans map.
    private final Map<Aliment, StockLine> stockMap = new HashMap<>();

    /**
     * Constructeur par défaut
     * @param stockLines
     */
    public Stock(List<StockLine> stockLines) {
        for (StockLine stockLine : stockLines) {
            stockMap.put(stockLine.getAliment(), stockLine);
        }
    }

    /**
     * Vérifie si uen liste d'ingrédients (aliment + quantité) est disponible dans le stock
     * @param requiredIngredients liste des ingrédients à tester
     * @return coveredIngredient, à true si tout est couvert, à false + liste non couverts sinon.
     */
    public CoveredIngredients covers(List<Ingredient> requiredIngredients){

        List<Ingredient> uncoveredIngredients = new ArrayList<>();
        CoveredIngredients coveredIngredients;
        boolean covered = true;

        //Calcul ingrédients présents en quantité suffisante ou non
        for(Ingredient ingredient : requiredIngredients) {
            double quantityStock = stockMap.get(ingredient.aliment()).getQuantity();
            if(ingredient.quantityPerPerson() > quantityStock) {
                covered = false;
                uncoveredIngredients.add(ingredient);
            }
        }

        coveredIngredients = new CoveredIngredients(covered, uncoveredIngredients);

        return coveredIngredients;
    }

}
