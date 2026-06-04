package io.github.oliviercap.chefduplacard.application.savenewmenu;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.Objects;

public class SaveNewMenuUseCase implements ISaveNewMenuInputPort {

    private final IMenuRepository menuRepository;
    private final ISaveNewMenuOutputPort outputPort;

    public SaveNewMenuUseCase(IMenuRepository menuRepository,
                              ISaveNewMenuOutputPort outputPort) {
        this.menuRepository = menuRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(SaveNewMenuRequestModel requestModel) {
        Objects.requireNonNull(requestModel, "requestModel must not be null");

        boolean saved = saveNewMenu(requestModel.newMenu()) ? true : false;
        outputPort.saved(new SaveNewMenuResponseModel(saved));
    }

    private boolean saveNewMenu(Menu menu) {
        Objects.requireNonNull(menu, "menu must not be null");

        try{
            menuRepository.save(menu);
        } catch (Exception e) {
            throw new DomainException("save of menu didn't work", e);
        }

        return true;
    }
}
