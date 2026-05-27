package io.github.oliviercap.chefduplacard.adapters.web.getaliments.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getaliments.GetAlimentsViewModel;
import io.github.oliviercap.chefduplacard.application.dto.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.getaliments.GetAlimentsResponseModel;
import io.github.oliviercap.chefduplacard.application.getaliments.ports.IGetAlimentsOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetAlimentsPresenter implements IGetAlimentsOutputPort {
    private GetAlimentsViewModel viewModel;

    @Override
    public void displayAliments(GetAlimentsResponseModel responseModel) {
        viewModel = new GetAlimentsViewModel(
                responseModel.alimentResponses().stream()
                        .map(this::toAlimentViewModel)
                        .toList()
        );
    }

    @Override
    public GetAlimentsViewModel getViewModel() {
        return viewModel;
    }

    private GetAlimentsViewModel.AlimentViewModel toAlimentViewModel(AlimentResponse alimentResponse) {
        return new GetAlimentsViewModel.AlimentViewModel(
                alimentResponse.name(),
                alimentResponse.description(),
                alimentResponse.active()
        );
    }
}
