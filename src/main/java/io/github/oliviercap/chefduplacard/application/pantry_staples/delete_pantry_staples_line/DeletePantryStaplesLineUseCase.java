package io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line;

import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports.IDeletePantryStaplesLineInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports.IDeletePantryStaplesLineOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;

public class DeletePantryStaplesLineUseCase implements IDeletePantryStaplesLineInputPort {

    private final IPantryStapleLineRepository lineRepository;
    private final IDeletePantryStaplesLineOutputPort outputPort;

    public DeletePantryStaplesLineUseCase(IPantryStapleLineRepository lineRepository,
                                          IDeletePantryStaplesLineOutputPort outputPort
    ) {
        this.lineRepository = lineRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(DeletePantryStaplesLineRequestModel requestModel) {
        boolean lineDeleted = deletePantryStapleLine(requestModel.pantryStaplesLineId()) ? true : false;
        outputPort.displayResponse(new DeletePantryStaplesLineResponseModel(lineDeleted));
    }

    private boolean deletePantryStapleLine(Long pantryStaplesLineId) {
        try {
            lineRepository.deletePantryStaplesLine(pantryStaplesLineId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
