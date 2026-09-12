package io.github.oliviercap.chefduplacard.adapters.web.menu.get_all_menus.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.menu.get_all_menus.GetAllMenusViewModel;
import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.GetAllMenusResponseModel;
import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.ports.IGetAllMenusOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetAllMenusPresenter implements IGetAllMenusOutputPort {
    @Override
    public void displayMenus(GetAllMenusResponseModel responseModel) {
        
    }

    @Override
    public GetAllMenusViewModel getViewModel() {
        return null;
    }
}
