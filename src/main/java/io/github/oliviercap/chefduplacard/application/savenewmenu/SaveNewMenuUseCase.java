package io.github.oliviercap.chefduplacard.application.savenewmenu;

import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.controllers.SaveNewMenuRequest;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaveNewMenuUseCase implements ISaveNewMenuInputPort {

    private final IMenuRepository menuRepository;
    private final IRecipeRepository recipeRepository;
    private final ISaveNewMenuOutputPort outputPort;

    public SaveNewMenuUseCase(IMenuRepository menuRepository,
                              IRecipeRepository recipeRepository,
                              ISaveNewMenuOutputPort outputPort) {
        this.menuRepository = menuRepository;
        this.recipeRepository = recipeRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(SaveNewMenuRequestModel requestModel) {
        Objects.requireNonNull(requestModel, "requestModel must not be null");

        boolean saved = saveNewMenu(requestModel.newMenuRecord()) ? true : false;
        outputPort.saved(new SaveNewMenuResponseModel(saved));
    }

    private boolean saveNewMenu(SaveNewMenuRequest newMenuRecord) {
        Objects.requireNonNull(newMenuRecord, "menu must not be null");

        //reconstruction d'un menu à partir des données
        Menu menu;
        List<MenuLine> menuLineList = new ArrayList<>();
        for (SaveNewMenuRequest.MenuLine menuLine : newMenuRecord.menuLines()) {
            //getRecipe
            Recipe recipe = recipeRepository.findByName(menuLine.recipeName())
                    .orElseThrow(() -> new DomainException("Recipe " + menuLine.recipeName() + " not found"));

            menuLineList.add(new MenuLine(recipe, menuLine.nbPerson()));
        }

        menu = new Menu(newMenuRecord.menuName(), menuLineList);

        try{
            menuRepository.save(menu);
        } catch (Exception e) {
            throw new DomainException("save of menu didn't work", e);
        }

        return true;
    }
}
