package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;

/**
 * An Ingredient is an aliment associated with its quantity per person in a recipe.
 * @param quantityPerPerson quantity of this aliment per person
 * @param aliment
 * @param unit unit used with the quantity
 */
public record Ingredient(double quantityPerPerson, Aliment aliment, Unit unit) {
}
