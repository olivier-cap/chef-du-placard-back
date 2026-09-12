package io.github.oliviercap.chefduplacard.application.menu.get_all_menus;

import io.github.oliviercap.chefduplacard.application.htttpresponse.MenuResponse;
import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.ports.IGetAllMenusInputPort;
import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.ports.IGetAllMenusOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.List;

public class GetAllMenusUseCase implements IGetAllMenusInputPort {

    private final IMenuRepository menuRepository;
    private final IGetAllMenusOutputPort outputPort;

    public GetAllMenusUseCase(
            IMenuRepository menuRepository,
            IGetAllMenusOutputPort outputPort
    ) {
        this.menuRepository = menuRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(GetAllMenusRequestModel requestModel) {
        List<Menu> menus = getAllMenus(requestModel.userId());

        outputPort.displayMenus(
                new GetAllMenusResponseModel(
                    menus.stream()
                            .map(MenuResponse::from)
                            .toList()
                )
        );
    }

    private List<Menu> getAllMenus(Long userId) {
        try {
            return menuRepository.findAllByUser(userId);
        } catch (Exception e) {
            throw new DomainException("menus not found",e);
        }
    }
}
