package io.github.oliviercap.chefduplacard.domain.recipe;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
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
public final class Recipe {
    private final RecipeId id;
    private final String name;
    private final String instructions;
    private final Duration duration;
    private final String difficulty;
    private final List<Ingredient> ingredients;


    public Recipe(RecipeId id, String name, String instructions, Duration duration, String difficulty, List<Ingredient> ingredients) {
        if(id == null){
            throw new DomainException("recipe id cannot be null");
        }
        if(name == null || name.isBlank()){
            throw new DomainException("recipe name cannot be blank or null");
        }
        if(ingredients == null || ingredients.isEmpty()) {
            throw new DomainException("a recipe must have at least one ingredient");
        }
        if(instructions == null || instructions.isBlank()) {
            throw new DomainException("a recipe must have a description");
        }
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.duration = duration == null ? Duration.ZERO : duration;
        this.difficulty = difficulty == null ? "" : difficulty;
        this.ingredients = List.copyOf(ingredients);
    }


    /**
     * Calculate quantity of ingredients for npPeople for this recipe.
     * @return a list of Ingredients with the required quantity
     */
    public List<Ingredient> computeRequiredIngredients(int nbPeople) {
        if (nbPeople <= 0) {
            throw new DomainException("number of people must be greater than 0");
        }

        List<Ingredient> requiredIngredients = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            BigDecimal requiredQuantity;

            //Calculation of ingredient for n person
            requiredQuantity = ingredient.getQuantity().multiply(BigDecimal.valueOf(nbPeople));

            requiredIngredients.add(
                    new Ingredient(
                            ingredient.getId(),
                            requiredQuantity,
                            ingredient.getAliment(),
                            ingredient.getUnit())
            );
        }

        return List.copyOf(requiredIngredients);
    }

    /** Getters **/

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public RecipeId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recipe recipe)) return false;
        return Objects.equals(id, recipe.id) && Objects.equals(name, recipe.name) && Objects.equals(instructions, recipe.instructions) && Objects.equals(duration, recipe.duration) && Objects.equals(difficulty, recipe.difficulty) && Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructions, duration, difficulty, ingredients);
    }
}
