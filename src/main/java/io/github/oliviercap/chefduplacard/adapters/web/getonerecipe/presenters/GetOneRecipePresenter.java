package io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.GetOneRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.htttpresponse.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.IngredientResponse;
import io.github.oliviercap.chefduplacard.application.htttpresponse.UnitResponse;
import io.github.oliviercap.chefduplacard.application.getonerecipe.GetOneRecipeResponseModel;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetOneRecipePresenter implements IGetOneRecipeOutputPort {

    private GetOneRecipeViewModel viewModel;

    @Override
    public void diplayOneRecipe(GetOneRecipeResponseModel responseModel) {
        viewModel = new GetOneRecipeViewModel(
                responseModel.recipeResponse().name(),
                responseModel.recipeResponse().instructions(),
                responseModel.recipeResponse().duration(),
                responseModel.recipeResponse().difficulty(),
                responseModel.recipeResponse().ingredients().stream()
                        .map(this::toIngredientViewModel)
                        .toList()
        );
    }

    @Override
    public GetOneRecipeViewModel getViewModel() {
        return viewModel;
    }

    private GetOneRecipeViewModel.IngredientViewModel toIngredientViewModel(IngredientResponse ingredientResponse) {
        return new GetOneRecipeViewModel.IngredientViewModel(
                ingredientResponse.quantityPerPerson(),
                toAlimentViewModel(ingredientResponse.alimentResponse()),
                toUnitViewModel(ingredientResponse.unitResponse())
        );
    }

    private GetOneRecipeViewModel.AlimentViewModel toAlimentViewModel(AlimentResponse alimentResponse) {
        return new GetOneRecipeViewModel.AlimentViewModel(
                alimentResponse.name(),
                alimentResponse.description(),
                alimentResponse.active()
        );
    }

    private GetOneRecipeViewModel.UnitViewModel toUnitViewModel(UnitResponse unitResponse) {
        return new GetOneRecipeViewModel.UnitViewModel(
                unitResponse.name(),
                unitResponse.symbol()
        );
    }
}
