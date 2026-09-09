package io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples.PantryStaplesRepository;
import io.github.oliviercap.chefduplacard.application.htttpresponse.PantryStaplesResponse;
import io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.ports.IFindByUserPantryStaplesInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.ports.IFindByUserPantryStaplesOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;

public class FindByUserPantryStaplesUseCase implements IFindByUserPantryStaplesInputPort {

    private final IPantryStaplesRepository pantryStaplesRepository;
    private final IFindByUserPantryStaplesOutputPort outputPort;

    public FindByUserPantryStaplesUseCase(
            IPantryStaplesRepository pantryStaplesRepository,
            IFindByUserPantryStaplesOutputPort outputPort
    ) {
        this.pantryStaplesRepository = pantryStaplesRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(FindByUserPantryStaplesRequestModel requestModel) {
        PantryStaples pantryStaples = findByUser(requestModel.userId());

        outputPort.displayPantryStaples(
                new FindByUserPantryStaplesResponseModel(
                        PantryStaplesResponse.from(
                                pantryStaples
                        )
                )
        );
    }

    private PantryStaples findByUser(Long userID) {
        try {
            return pantryStaplesRepository.findByUserId(userID);
        } catch (Exception e) {
            throw new RuntimeException("error find user", e);
        }
    }
}
