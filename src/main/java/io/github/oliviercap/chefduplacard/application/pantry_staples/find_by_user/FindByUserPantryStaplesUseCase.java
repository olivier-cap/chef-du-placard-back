package io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user;

import io.github.oliviercap.chefduplacard.application.htttpresponse.PantryStaplesResponse;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports.IFindByUserPantryStaplesInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports.IFindByUserPantryStaplesOutputPort;
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
