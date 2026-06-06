package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusResponseModel;
import io.github.oliviercap.chefduplacard.application.htttpresponse.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.UnitResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CookableMenusPresenterTest {

    @Test
    void should_transform_menus_response_model_to_view_model() {

        // GIVEN
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

        CookableMenusResponseModel responseModel =
                new CookableMenusResponseModel(
                        true,
                        List.of(recipeResponse),
                        "message"
                );

        CookableMenusPresenter presenter = new CookableMenusPresenter();

        // WHEN
        presenter.displayCookableMenus(responseModel);

        CookableMenusViewModel result = presenter.getViewModel();

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

        assertThat(result.covered()).isTrue();
        assertThat(result.message()).isEqualTo("message");
    }
}
