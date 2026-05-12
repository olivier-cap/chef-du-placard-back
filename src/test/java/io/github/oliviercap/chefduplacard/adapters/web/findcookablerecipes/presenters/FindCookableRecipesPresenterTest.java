package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.application.dto.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.dto.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.dto.UnitResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindCookableRecipesPresenterTest {

    @Test
    void should_transform_recipe_response_to_presenter_model() {

        IngredientResponse ingredient = new IngredientResponse(
                BigDecimal.valueOf(3),
                new AlimentResponse("apple", "fruit", true),
                new UnitResponse("gramme", "g")
        );

        RecipeResponse recipeResponse = new RecipeResponse(
                "r1",
                "instructions",
                Duration.ofMinutes(5),
                "easy",
                List.of(ingredient)
        );

        FindCookableRecipesPresenter presenter = new FindCookableRecipesPresenter();

        FindCookableRecipesResponseModel result =
                presenter.displayCookableRecipes(List.of(recipeResponse));

        assertThat(result.recipes())
                .hasSize(1)
                .first()
                .satisfies(recipe -> {
                    assertThat(recipe.recipeName()).isEqualTo("r1");
                    assertThat(recipe.duration()).isEqualTo(Duration.ofMinutes(5));
                    assertThat(recipe.ingredients()).hasSize(1);
                    assertThat(recipe.ingredients().getFirst().alimentName())
                            .isEqualTo("apple");
                });
    }
}
