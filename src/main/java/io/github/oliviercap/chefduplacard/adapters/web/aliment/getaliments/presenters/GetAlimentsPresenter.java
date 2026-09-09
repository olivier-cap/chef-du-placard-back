package io.github.oliviercap.chefduplacard.adapters.web.aliment.getaliments.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.getaliments.GetAlimentsViewModel;
import io.github.oliviercap.chefduplacard.application.htttpresponse.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.GetAlimentsResponseModel;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.ports.IGetAlimentsOutputPort;
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
                alimentResponse.id(),
                alimentResponse.name(),
                alimentResponse.description(),
                alimentResponse.active()
        );
    }
}
