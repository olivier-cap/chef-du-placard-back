package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.math.BigDecimal;
import java.util.List;

/**
 * Réponse à la question "liste d'aliments est disponible dans le stock ?"
 * @param covered à true si tous les ingrédients sont disponibles, false sinon
 * @param uncoveredIngredients contient la liste des ingrédients, avec leurs quantités,
 *                             en quantités insuffisantes
 */
public record CoveredIngredients(
        boolean covered,
        List<Uncovered> uncoveredIngredients
) {
    public record Uncovered(
            Ingredient ingredient,
            BigDecimal missingQuantity
    ){}
}
