package io.github.oliviercap.chefduplacard.domain.recipe;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RecipeTest {

    @Test
    void calculate_quantity_of_ingredients_for_nPeople(){
        //With
        Aliment apple = new Aliment("apple", "fruit", true);
        Aliment grapefruit = new Aliment("grapefruit", "fruit", true);

        Unit unit = new Unit("gramme","g");

        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(2);
        Ingredient ingredient1 = new Ingredient(a, apple, unit);
        Ingredient ingredient2 = new Ingredient(b,grapefruit, unit);

        //When
        Recipe recipe = new Recipe("test", "instructions very complex", Duration.ofMinutes(12), "3", List.of(ingredient1, ingredient2));

        int nbPeople = 5;
        BigDecimal c = BigDecimal.valueOf(25);
        BigDecimal d = BigDecimal.valueOf(10);
        Ingredient ingredient1_2 = new Ingredient(c, apple, unit);
        Ingredient ingredient2_2 = new Ingredient(d,grapefruit, unit);
        List<Ingredient> attendedResult = List.of(ingredient1_2, ingredient2_2);

        //Then
        //Ne passera pas avant d'avoir rélgé les questions de Equals et Hash dnas recipe, aliment, ingredient etc.
        //assertThat(recipe.computeRequiredIngredients(nbPeople)).isEqualTo(attendedResult);
        assertThat(recipe.computeRequiredIngredients(nbPeople))
                .usingRecursiveComparison()
                .isEqualTo(attendedResult);

        //Version equals implanté
        assertThat(recipe.computeRequiredIngredients(nbPeople)).isEqualTo(attendedResult);
    }
}
