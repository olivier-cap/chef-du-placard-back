package io.github.oliviercap.chefduplacard.application.getmenu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.MenuRepository;
import io.github.oliviercap.chefduplacard.application.dto.MenuResponse;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuInputPort;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.Objects;

public class GetMenuUseCase implements IGetMenuInputPort {

    private final MenuRepository menuRepository;
    private final IGetMenuOutputPort outputPort;

    public GetMenuUseCase(MenuRepository menuRepository,
                          IGetMenuOutputPort outputPort) {
        this.menuRepository = menuRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetMenuRequestModel requestModel) {
        Objects.requireNonNull(requestModel, "requestModel must not be null");

        Menu menu = getMenu(requestModel.menuName());
        outputPort.displayMenu(new GetMenuResponseModel(MenuResponse.from(menu)));
    }

    private Menu getMenu(String menuName) {
        if(menuName.isBlank()) {
            throw new DomainException("menuName must not be blank");
        }
        return menuRepository.findByName(menuName).orElseThrow(() -> new DomainException("menu not found " + menuName));
    }
}
