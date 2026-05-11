package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.dto.IngredientForPresenter;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.dto.RecipeForPresenter;
import io.github.oliviercap.chefduplacard.application.dto.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@Component
public class FindCookableRecipesPresenter implements IFindCookableRecipesOutputPort {
    @Override
    public FindCookableRecipesResponseModel displayCookableRecipes(List<RecipeResponse> recipeResponse) {
        //liste de recipes aplanis
        return new FindCookableRecipesResponseModel(
                recipeResponse.stream()
                        .map(this::toRecipeResponseModel)
                        .toList()
        );
    }

    private RecipeForPresenter toRecipeResponseModel(RecipeResponse recipeResponse) {
        return new RecipeForPresenter(
                recipeResponse.name(),
                recipeResponse.instructions(),
                recipeResponse.duration(),
                recipeResponse.difficulty(),
                recipeResponse.ingredients().stream()
                        .map(this::toIngredientResponseModel)
                        .toList()
        );
    }

    private IngredientForPresenter toIngredientResponseModel(IngredientResponse ingredientResponse) {
        return new IngredientForPresenter(
                ingredientResponse.quantityPerPerson(),
                ingredientResponse.alimentResponse().name(),
                ingredientResponse.unitResponse().symbol()
        );
    }
}

