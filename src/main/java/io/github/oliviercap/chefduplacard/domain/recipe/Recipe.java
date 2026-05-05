package io.github.oliviercap.chefduplacard.domain.recipe;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            BigDecimal requiredQuantity;

            //Calculation of ingredient for n person
            requiredQuantity = ingredient.getQuantityPerPerson().multiply(BigDecimal.valueOf(nbPeople));

            requiredIngredients.add(new Ingredient(requiredQuantity, ingredient.getAliment(), ingredient.getUnit()));
        }

        return requiredIngredients;
    }

    /** Getters and Setters **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recipe recipe)) return false;
        return Objects.equals(name, recipe.name) && Objects.equals(instructions, recipe.instructions) && Objects.equals(duration, recipe.duration) && Objects.equals(difficulty, recipe.difficulty) && Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instructions, duration, difficulty, ingredients);
    }
}
