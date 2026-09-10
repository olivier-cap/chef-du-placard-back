package io.github.oliviercap.chefduplacard.adapters.web.unit.get_all_units.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.unit.get_all_units.GetAllUnitsViewModel;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.GetAllUnitsResponseModel;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports.IGetAllUnitsOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetAllUnitsPresenter implements IGetAllUnitsOutputPort {

    private GetAllUnitsViewModel viewModel;

    @Override
    public void displayUnits(GetAllUnitsResponseModel responseModel) {
        viewModel = new GetAllUnitsViewModel(
                responseModel.responses().stream()
                        .map(u-> new GetAllUnitsViewModel.Unit(
                                u.id(),
                                u.symbol(),
                                u.name()
                        )).toList()
        );
    }

    @Override
    public GetAllUnitsViewModel getViewModel() {
        return viewModel;
    }
}
