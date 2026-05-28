package io.github.oliviercap.chefduplacard.application.createaliment;

import io.github.oliviercap.chefduplacard.application.createaliment.ports.ICreateAlimentInputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

public class CreateAlimentUseCase implements ICreateAlimentInputPort {

    private final ICreateAlimentInputPort alimentInputPort;
    private final IAlimentRepository alimentRepository;

    public CreateAlimentUseCase(ICreateAlimentInputPort alimentInputPort,
                                IAlimentRepository alimentRepository) {
        this.alimentInputPort = alimentInputPort;
        this.alimentRepository = alimentRepository;
    }


    @Override
    public void execute(CreateAlimentRequestModel requestModel) {
        createAliment(requestModel.nomAliment(), requestModel.descriptionAliment(), requestModel.isAtive());
    }

    private void createAliment(String nomAliment, String descriptionAliment, boolean isActive) {
        String responseMessage;

        Aliment newAliment = new Aliment(nomAliment, descriptionAliment, isActive);

        //Vérification de l'intégrité des données du nouvel aliment
        if(!newAliment.check()) {
            throw new DomainException("Incorrect data for the new ingredient");
        }

        //Vérification si l'aliment existe déjà ou non dans la base
        if(alimentRepository.findAlimentByName(nomAliment).isPresent()) {
            responseMessage = "Aliment name already used";
        }
        else {
            //Enregistrement du nouvel aliment
            try{
                alimentRepository.save(newAliment);
            } catch (Exception e) {
                throw new DomainException("Impossible to save the new Aliment" + newAliment.getName());
            }
        }
    }
}
