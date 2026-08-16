package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesViewModel;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.application.htttpresponse.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.UnitResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindCookableRecipesPresenterTest {

    @Test
    void should_transform_recipe_response_model_to_view_model() {

        // GIVEN
        IngredientResponse ingredient = new IngredientResponse(
                Long.valueOf(1),
                BigDecimal.valueOf(3),
                new AlimentResponse(Long.valueOf(1),"apple", "fruit", true),
                new UnitResponse(Long.valueOf(1),"gramme", "g")
        );

        RecipeResponse recipeResponse = new RecipeResponse(
                Long.valueOf(1),
                "r1",
                "instructions",
                Duration.ofMinutes(5),
                "easy",
                List.of(ingredient)
        );

        FindCookableRecipesResponseModel responseModel =
                new FindCookableRecipesResponseModel(
                        List.of(recipeResponse)
                );

        FindCookableRecipesPresenter presenter = new FindCookableRecipesPresenter();

        // WHEN
        presenter.displayCookableRecipes(responseModel);

        FindCookableRecipesViewModel result = presenter.getViewModel();

        // THEN
        assertThat(result).isNotNull();

        assertThat(result.recipes())
                .hasSize(1)
                .first()
                .satisfies(recipe -> {
                    assertThat(recipe.recipeName()).isEqualTo("r1");
                    assertThat(recipe.recipeInstructions()).isEqualTo("instructions");
                    assertThat(recipe.duration()).isEqualTo(Duration.ofMinutes(5));
                    assertThat(recipe.difficulty()).isEqualTo("easy");

                    assertThat(recipe.ingredients()).hasSize(1);
                    assertThat(recipe.ingredients().getFirst().quantityPerPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(3));
                    assertThat(recipe.ingredients().getFirst().alimentName())
                            .isEqualTo("apple");
                    assertThat(recipe.ingredients().getFirst().unitSymbol())
                            .isEqualTo("g");
                });
    }
}