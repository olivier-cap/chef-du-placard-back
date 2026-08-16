package io.github.oliviercap.chefduplacard.application.getmenu;

import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuInputPort;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.query.IMenuViewQuery;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.List;
import java.util.Objects;

public class GetMenuUseCase implements IGetMenuInputPort {

    private final IMenuViewQuery menuViewQuery;
    private final IGetMenuOutputPort outputPort;

    public GetMenuUseCase(IMenuViewQuery menuViewQuery,
                          IGetMenuOutputPort outputPort) {
        this.menuViewQuery = menuViewQuery;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetMenuRequestModel requestModel) {
        Objects.requireNonNull(requestModel, "requestModel must not be null");

        outputPort.displayMenu(new GetMenuResponseModel(
                getMenu(requestModel.menuId())
        ));
    }

    private List<GetMenuQuery> getMenu(Long menuId) {
        if(menuId == null) {
            throw new DomainException("menuId must not be blank");
        }

        List<GetMenuQuery> getMenuQueryList =menuViewQuery.getViewMenu(menuId);

        if (getMenuQueryList.isEmpty()) {
            throw new DomainException("menu not found " + menuId);
        }

        return getMenuQueryList;
    }
}
