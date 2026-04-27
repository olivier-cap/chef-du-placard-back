package io.github.oliviercap.chefduplacard.domain.recipe;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Recipe est une recette, dans le domaine (données métier, encapsulées).
 * Recipe contient les propriétés de la recette.
 */
public class Recipe {
    private String name;
    private String instructions;
    private Duration duration;
    private String difficulty;
    private List<Ingredient> ingredients;
    private List<Ingredient> requiredIngredients;


    public Recipe(String name, String instructions, Duration duration, String difficulty, List<Ingredient> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.duration = duration;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
    }


    /**
     * Calculate quantity of ingredients for npPeople for this recipe.
     * @param nbPeople
     * @return a list of Ingredients with the required quantity
     */
    public List<Ingredient> computeRequiredIngredients(int nbPeople) {
        requiredIngredients = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            double requiredQuantity;
            requiredQuantity = ingredient.quantityPerPerson() * nbPeople;
            requiredIngredients.add(new Ingredient(requiredQuantity, ingredient.aliment(), ingredient.unit()));
        }

        return requiredIngredients;
    }


}
