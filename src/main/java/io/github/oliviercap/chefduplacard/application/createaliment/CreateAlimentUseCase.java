package io.github.oliviercap.chefduplacard.application.createaliment;

import io.github.oliviercap.chefduplacard.application.createaliment.ports.ICreateAlimentInputPort;
import io.github.oliviercap.chefduplacard.application.createaliment.ports.ICreateAlimentOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

public class CreateAlimentUseCase implements ICreateAlimentInputPort {

    private final ICreateAlimentOutputPort outputPort;
    private final IAlimentRepository alimentRepository;

    public CreateAlimentUseCase(ICreateAlimentOutputPort outputPort,
                                IAlimentRepository alimentRepository) {
        this.outputPort = outputPort;
        this.alimentRepository = alimentRepository;
    }


    @Override
    public void execute(CreateAlimentRequestModel requestModel) {
        String response = createAliment(requestModel.nomAliment(), requestModel.descriptionAliment(), requestModel.isActive());
        outputPort.createAlimentResponse(new CreateAlilmentResponseModel(response));
    }

    private String createAliment(String nomAliment, String descriptionAliment, boolean isActive) {

        Aliment newAliment = new Aliment(nomAliment, descriptionAliment, isActive);

        // validation métier
        if(!newAliment.check()) {
            throw new DomainException("Incorrect data for the new ingredient");
        }

        // doublon
        if(alimentRepository.findAlimentByName(nomAliment).isPresent()) {
            return "Aliment name already used";
        }

        // persistence
        try{
            alimentRepository.save(newAliment);
        } catch (Exception e) {
            throw new DomainException("Impossible to save the new Aliment " + newAliment.getName());
        }

        return "Aliment saved";
    }
}
