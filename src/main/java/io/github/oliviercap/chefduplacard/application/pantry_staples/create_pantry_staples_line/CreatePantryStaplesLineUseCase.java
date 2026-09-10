package io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line;

import io.github.oliviercap.chefduplacard.application.htttpresponse.PantryStaplesLineResponse;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports.ICreatePantryStaplesLineInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports.ICreatePantryStaplesLineOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;

import java.math.BigDecimal;

public class CreatePantryStaplesLineUseCase implements ICreatePantryStaplesLineInputPort {

    private final IPantryStapleLineRepository lineRepository;
    private final ICreatePantryStaplesLineOutputPort outputPort;

    public CreatePantryStaplesLineUseCase(
            IPantryStapleLineRepository lineRepository,
            ICreatePantryStaplesLineOutputPort outputPort
    ) {
        this.lineRepository = lineRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(CreatePantryStaplesLineRequestModel requestModel) {
        PantryStaplesLine pantryStaplesLine = createPantryStaplesLine(
                requestModel.pantryStaplesId(), requestModel.alimentId(), requestModel.quantity(), requestModel.unitId()
        );

        outputPort.displayNewPantryStaplesLine(new CreatePantryStaplesLineResponseModel(
                PantryStaplesLineResponse.from(pantryStaplesLine)
        ));
    }

    private PantryStaplesLine createPantryStaplesLine(Long pantryStaplesId, Long alimentId, BigDecimal quantity, Long unitId) {
        try {
            return lineRepository.createPantryStaplesLine(pantryStaplesId, alimentId, quantity, unitId);
        } catch (Exception e) {
            throw new DomainException("Error create new pantry staples line",e);
        }
    }
}
