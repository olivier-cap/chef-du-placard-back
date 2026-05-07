package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.math.BigDecimal;
import java.util.*;

/**
 * Stock d'aliments.
 * Le stock est représenté par un ensemble de lignes aliment <-> quantité.
 */
public final class Stock {
    //Pour permettre recherches par aliment, stockage des données dans map.
    private final Map<Aliment, StockLine> stockMap = new HashMap<>();
    private final String name;

    /**
     * Constructeur par défaut
     * @param stockLines
     */
    public Stock(String name, List<StockLine> stockLines) {
        if(name == null || name.isBlank()){
            throw new DomainException("stock name cannot be blank or null");
        }

        if (stockLines == null) {
            throw new DomainException("stock lines cannot be null");
        }

        this.name = name;

        for (StockLine stockLine : stockLines) {
            if (stockLine == null) {
                throw new DomainException("stock line cannot be null");
            }

            if (stockMap.containsKey(stockLine.getAliment())) {
                throw new DomainException("stock cannot contain duplicate aliment lines");
            }

            stockMap.put(stockLine.getAliment(), stockLine);
        }
    }

    /**
     * Vérifie si une liste d'ingrédients (aliment + quantité) est disponible dans le stock
     * @param requiredIngredients liste des ingrédients à tester
     * @return coveredIngredient, à true si tout est couvert, à false + liste non couverts sinon.
     */
    public CoveredIngredients covers(List<Ingredient> requiredIngredients){

        List<Ingredient> uncoveredIngredients = new ArrayList<>();
        CoveredIngredients coveredIngredients;
        boolean covered = true;

        //liste d'ingrédients vide
        /*if (requiredIngredients.isEmpty()) {
            covered = false;
            return new CoveredIngredients(covered, uncoveredIngredients);
        }*/

        //Calcul ingrédients présents en quantité suffisante ou non
        for(Ingredient ingredient : requiredIngredients) {
            boolean alimentIsInStock = stockMap.containsKey(ingredient.getAliment());
            if(alimentIsInStock) {
                BigDecimal quantityStock = stockMap.get(ingredient.getAliment()).getQuantity();
                if(ingredient.getQuantityPerPerson().compareTo(quantityStock) > 0) {
                    covered = false;
                    uncoveredIngredients.add(ingredient);
                }
            }
            else {
                covered = false;
                uncoveredIngredients.add(ingredient);
            }
        }

        coveredIngredients = new CoveredIngredients(covered, uncoveredIngredients);

        return coveredIngredients;
    }

    /** Getters and Setters **/

    public Map<Aliment, StockLine> getStockMap() {
        return Map.copyOf(stockMap);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stock stock)) return false;
        return Objects.equals(stockMap, stock.stockMap) && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockMap, name);
    }
}
