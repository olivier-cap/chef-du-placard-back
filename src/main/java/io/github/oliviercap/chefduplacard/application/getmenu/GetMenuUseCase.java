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
                getMenu(requestModel.menuName())
        ));
    }

    private List<GetMenuQuery> getMenu(String menuName) {
        if(menuName.isBlank()) {
            throw new DomainException("menuName must not be blank");
        }

        List<GetMenuQuery> getMenuQueryList =menuViewQuery.getViewMenu(menuName);

        if (getMenuQueryList.isEmpty()) {
            throw new DomainException("menu not found " + menuName);
        }

        return getMenuQueryList;
    }
}
