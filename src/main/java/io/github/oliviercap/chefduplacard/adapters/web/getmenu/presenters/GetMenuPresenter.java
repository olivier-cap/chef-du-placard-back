package io.github.oliviercap.chefduplacard.adapters.web.getmenu.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.application.dto.MenuLineResponse;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuResponseModel;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetMenuPresenter implements IGetMenuOutputPort {

    private GetMenuViewModel viewModel;

    @Override
    public void displayMenu(GetMenuResponseModel responseModel) {
        viewModel = new GetMenuViewModel(
                responseModel.menuResponse().menuName(),
                responseModel.menuResponse().menuLineResponseList().stream()
                        .map(this::toMenuLineViewModel)
                        .toList()
        );
    }

    @Override
    public GetMenuViewModel getViewModel() {
        return viewModel;
    }

    private GetMenuViewModel.MenuLineViewModel toMenuLineViewModel(MenuLineResponse menuLineResponse) {
        return new GetMenuViewModel.MenuLineViewModel(
                menuLineResponse.nbPerson(),
                toRecipeViewModel(menuLineResponse.recipeResponse())
        );
    }

    private GetMenuViewModel.RecipeViewModel toRecipeViewModel(RecipeResponse recipeResponse) {
        return new GetMenuViewModel.RecipeViewModel(
                recipeResponse.name(),
                recipeResponse.instructions(),
                recipeResponse.duration(),
                recipeResponse.difficulty()
        );
    }
}
