package io.github.oliviercap.chefduplacard.adapters.web.getmenu.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuQuery;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuResponseModel;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuOutputPort;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class GetMenuPresenter implements IGetMenuOutputPort {

    private GetMenuViewModel viewModel;

    @Override
    public void displayMenu(GetMenuResponseModel responseModel) {
        viewModel = new GetMenuViewModel(
                responseModel.getMenuQuery().getFirst().menuName(),
                responseModel.getMenuQuery().stream()
                        .map(this::toMenuLineViewModel)
                        .toList()
        );
    }

    @Override
    public GetMenuViewModel getViewModel() {
        return viewModel;
    }

    private GetMenuViewModel.MenuLineViewModel toMenuLineViewModel(GetMenuQuery getMenuQuery) {
        return new GetMenuViewModel.MenuLineViewModel(
                getMenuQuery.nbPerson(),
                toRecipeViewModel(
                        getMenuQuery.name(),
                        getMenuQuery.instructions(),
                        getMenuQuery.duration(),
                        getMenuQuery.difficulty()
                ));
    }

    private GetMenuViewModel.RecipeViewModel toRecipeViewModel(String name, String instructions, Integer duration, String difficulty) {
        return new GetMenuViewModel.RecipeViewModel(
                name,
                instructions,
                Duration.ofMinutes(duration),
                difficulty
        );
    }
}
