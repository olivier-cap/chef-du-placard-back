package io.github.oliviercap.chefduplacard.application.savenewmenu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.SaveNewMenuDTO;
import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.controllers.SaveNewMenuRequest;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

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

        if (newMenuRecord.menuName() == null
                || newMenuRecord.menuName().isBlank()) {
            throw new DomainException("menu name must not be blank");
        }

        //reconstruction d'un menuDTO à partir des données

        SaveNewMenuDTO menuDTO = new SaveNewMenuDTO(
                newMenuRecord.menuName(),
                newMenuRecord.menuLines().stream()
                        .map(
                                ml -> new SaveNewMenuDTO.saveNewMenuLine(
                                        ml.recipeId(),
                                        ml.nbPerson()
                                )
                        ).toList()
        );

        try{
            menuRepository.save(menuDTO);
        } catch (Exception e) {
            throw new DomainException("save of menu didn't work", e);
        }

        return true;
    }
}
