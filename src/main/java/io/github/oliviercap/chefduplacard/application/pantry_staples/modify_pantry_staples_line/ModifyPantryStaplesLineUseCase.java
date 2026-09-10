package io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line;

import io.github.oliviercap.chefduplacard.application.htttpresponse.PantryStaplesLineResponse;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports.IModifyPantryStaplesLineInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports.IModifyPantryStaplesLineOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;

import java.math.BigDecimal;

public class ModifyPantryStaplesLineUseCase implements IModifyPantryStaplesLineInputPort {

    private final IPantryStapleLineRepository lineRepository;
    private final IModifyPantryStaplesLineOutputPort outputPort;

    public ModifyPantryStaplesLineUseCase(
            IPantryStapleLineRepository lineRepository,
            IModifyPantryStaplesLineOutputPort outputPort
    ) {
        this.lineRepository = lineRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(ModifyPantryStaplesLineRequestModel requestModel) {
        PantryStaplesLine pantryStaplesLine = ModifyPantryStaplesLine(requestModel.pantryStapleLineId(), requestModel.quantity(), requestModel.unitId());
        outputPort.displayNewPantryStapleLine(
                new ModifyPantryStaplesLineResponseModel(
                        PantryStaplesLineResponse.from(pantryStaplesLine)
                )
        );
    }

    private PantryStaplesLine ModifyPantryStaplesLine(Long pantryStaplesLineId, BigDecimal quantity, Long unitId) {
        return lineRepository.modifyPantryStaplesLine(pantryStaplesLineId, quantity, unitId);
    }
}
