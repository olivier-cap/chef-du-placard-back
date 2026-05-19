package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters;


import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesViewModel;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesOutputPort;
import io.github.oliviercap.chefduplacard.application.dto.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class FindCookableRecipesPresenter implements IFindCookableRecipesOutputPort {

    private FindCookableRecipesViewModel viewModel;

    public void displayCookableRecipes(FindCookableRecipesResponseModel responseModel) {
        this.viewModel = new FindCookableRecipesViewModel(
                responseModel.recipeResponses().stream()
                        .map(this::toRecipeViewModel)
                        .toList()
        );
    }

    private FindCookableRecipesViewModel.RecipeViewModel toRecipeViewModel(RecipeResponse recipeResponse) {
        return new FindCookableRecipesViewModel.RecipeViewModel(
                recipeResponse.name(),
                recipeResponse.instructions(),
                recipeResponse.duration(),
                recipeResponse.difficulty(),
                recipeResponse.ingredients().stream()
                        .map(this::toIngredientViewModel)
                        .toList()
        );
    }

    private FindCookableRecipesViewModel.IngredientViewModel toIngredientViewModel(IngredientResponse ingredientResponse) {
        return new FindCookableRecipesViewModel.IngredientViewModel(
                ingredientResponse.quantityPerPerson(),
                ingredientResponse.alimentResponse().name(),
                ingredientResponse.unitResponse().symbol()
        );
    }

    public FindCookableRecipesViewModel getViewModel() {
        return viewModel;
    }
}