package io.github.oliviercap.chefduplacard.application.getaliments;

import io.github.oliviercap.chefduplacard.application.htttpresponse.AlimentResponse;
import io.github.oliviercap.chefduplacard.application.getaliments.ports.IGetAlimentsInputPort;
import io.github.oliviercap.chefduplacard.application.getaliments.ports.IGetAlimentsOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

import java.util.List;

/**
 * UseCase to view of aliments existing in the database
 */
public class GetAlimentsUseCase implements IGetAlimentsInputPort {
    private final IAlimentRepository alimentRepository;
    private final IGetAlimentsOutputPort outputPort;

    public GetAlimentsUseCase(IAlimentRepository alimentRepository,
                              IGetAlimentsOutputPort outputPort) {
        this.alimentRepository = alimentRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(GetAlimentsRequestModel requestModel) {
        List<Aliment> aliments = getAliments();
        outputPort.displayAliments(
                new GetAlimentsResponseModel(
                        aliments.stream()
                                .map(AlimentResponse::from)
                                .toList()
                )
        );

    }

    private List<Aliment> getAliments() {
        return alimentRepository.findAll();
    }
}
