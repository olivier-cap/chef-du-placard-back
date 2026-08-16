package io.github.oliviercap.chefduplacard.domain.recipe;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.food.IngredientId;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecipeTest {

    private static final RecipeId RECIPE_ID = new RecipeId(1L);

    private static final Aliment APPLE = new Aliment(
            new AlimentId(1L),
            "apple",
            "fruit",
            true
    );

    private static final Aliment GRAPEFRUIT = new Aliment(
            new AlimentId(2L),
            "grapefruit",
            "fruit",
            true
    );

    private static final Unit GRAM = new Unit(
            new UnitId(1L),
            "gramme",
            "g"
    );

    private static final Ingredient APPLE_INGREDIENT = new Ingredient(
            new IngredientId(1L),
            BigDecimal.valueOf(5),
            APPLE,
            GRAM
    );

    private static final Ingredient GRAPEFRUIT_INGREDIENT = new Ingredient(
            new IngredientId(2L),
            BigDecimal.valueOf(2),
            GRAPEFRUIT,
            GRAM
    );

    private static final List<Ingredient> INGREDIENTS = List.of(
            APPLE_INGREDIENT,
            GRAPEFRUIT_INGREDIENT
    );

    @Test
    void should_compute_required_ingredient_quantities_for_number_of_people() {
        Recipe recipe = createValidRecipe();
        int numberOfPeople = 5;

        List<Ingredient> expectedIngredients = List.of(
                new Ingredient(
                        new IngredientId(1L),
                        BigDecimal.valueOf(25),
                        APPLE,
                        GRAM
                ),
                new Ingredient(
                        new IngredientId(2L),
                        BigDecimal.valueOf(10),
                        GRAPEFRUIT,
                        GRAM
                )
        );

        List<Ingredient> actualIngredients =
                recipe.computeRequiredIngredients(numberOfPeople);

        assertThat(actualIngredients).isEqualTo(expectedIngredients);
    }

    @Test
    void should_reject_null_recipe_name() {
        assertThatThrownBy(() -> new Recipe(
                RECIPE_ID,
                null,
                "instructions very complex",
                Duration.ofMinutes(12),
                "3",
                INGREDIENTS
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("recipe name cannot be blank or null");
    }

    @Test
    void should_reject_blank_recipe_name() {
        assertThatThrownBy(() -> new Recipe(
                RECIPE_ID,
                " ",
                "instructions very complex",
                Duration.ofMinutes(12),
                "3",
                INGREDIENTS
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("recipe name cannot be blank or null");
    }

    @Test
    void should_reject_null_ingredient_list() {
        assertThatThrownBy(() -> new Recipe(
                RECIPE_ID,
                "test",
                "instructions very complex",
                Duration.ofMinutes(12),
                "3",
                null
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("a recipe must have at least one ingredient");
    }

    @Test
    void should_reject_empty_ingredient_list() {
        assertThatThrownBy(() -> new Recipe(
                RECIPE_ID,
                "test",
                "instructions very complex",
                Duration.ofMinutes(12),
                "3",
                List.of()
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("a recipe must have at least one ingredient");
    }

    @Test
    void should_reject_null_instructions() {
        assertThatThrownBy(() -> new Recipe(
                RECIPE_ID,
                "test",
                null,
                Duration.ofMinutes(12),
                "3",
                INGREDIENTS
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("a recipe must have a description");
    }

    @Test
    void should_reject_blank_instructions() {
        assertThatThrownBy(() -> new Recipe(
                RECIPE_ID,
                "test",
                " ",
                Duration.ofMinutes(12),
                "3",
                INGREDIENTS
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("a recipe must have a description");
    }

    @Test
    void should_use_zero_duration_when_duration_is_null() {
        Recipe recipe = new Recipe(
                RECIPE_ID,
                "test",
                "instructions very complex",
                null,
                "3",
                INGREDIENTS
        );

        assertThat(recipe.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void should_use_empty_difficulty_when_difficulty_is_null() {
        Recipe recipe = new Recipe(
                RECIPE_ID,
                "test",
                "instructions very complex",
                Duration.ofMinutes(12),
                null,
                INGREDIENTS
        );

        assertThat(recipe.getDifficulty()).isEmpty();
    }

    @Test
    void should_reject_number_of_people_equal_to_zero() {
        Recipe recipe = createValidRecipe();

        assertThatThrownBy(() -> recipe.computeRequiredIngredients(0))
                .isInstanceOf(DomainException.class)
                .hasMessage("number of people must be greater than 0");
    }

    @Test
    void should_reject_negative_number_of_people() {
        Recipe recipe = createValidRecipe();

        assertThatThrownBy(() -> recipe.computeRequiredIngredients(-1))
                .isInstanceOf(DomainException.class)
                .hasMessage("number of people must be greater than 0");
    }

    private Recipe createValidRecipe() {
        return new Recipe(
                RECIPE_ID,
                "test",
                "instructions very complex",
                Duration.ofMinutes(12),
                "3",
                INGREDIENTS
        );
    }
}