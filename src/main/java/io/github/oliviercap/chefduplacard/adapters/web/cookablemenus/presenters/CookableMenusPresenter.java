package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.presenters;

import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusResponseModel;
import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.application.cookablemenus.ports.ICookableMenusOutputPort;
import io.github.oliviercap.chefduplacard.application.htttpresponse.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class CookableMenusPresenter implements ICookableMenusOutputPort {

    private CookableMenusViewModel viewModel;

    @Override
    public void displayCookableMenus(CookableMenusResponseModel responseModel) {
        this.viewModel = new CookableMenusViewModel(
                responseModel.nbMealCovered(),
                responseModel.recipes().stream()
                        .map(this::toRecipeViewModel)
                        .toList(),
                responseModel.message()
        );
    }

    public CookableMenusViewModel getViewModel() {
        return viewModel;
    }

    private CookableMenusViewModel.RecipeViewModel toRecipeViewModel(RecipeResponse recipeResponse) {
        return new CookableMenusViewModel.RecipeViewModel(
                recipeResponse.id(),
                recipeResponse.name(),
                recipeResponse.instructions(),
                recipeResponse.duration(),
                recipeResponse.difficulty(),
                recipeResponse.ingredients().stream()
                        .map(this::toIngredientViewModel)
                        .toList()
        );
    }

    private CookableMenusViewModel.IngredientViewModel toIngredientViewModel(IngredientResponse ingredientResponse) {
        return new CookableMenusViewModel.IngredientViewModel(
                ingredientResponse.id(),
                ingredientResponse.quantityPerPerson(),
                ingredientResponse.alimentResponse().name(),
                ingredientResponse.unitResponse().symbol()
        );
    }
}