package io.github.oliviercap.chefduplacard.application.modifyaliment;

import io.github.oliviercap.chefduplacard.application.modifyaliment.ports.IModifyAlimentInputPort;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ports.IModifyAlimentOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

/**
 * Modification des propriétés de l'aliment.
 * A date : son nom et sa description peuvent être modifiés par ce usecase.
 */
public class ModifyAlimentUseCase implements IModifyAlimentInputPort {

    private final IAlimentRepository alimentRepository;
    private final IModifyAlimentOutputPort outputPort;

    public ModifyAlimentUseCase(IAlimentRepository alimentRepository,
                                IModifyAlimentOutputPort outputPort) {
        this.alimentRepository = alimentRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(ModifyAlimentRequestModel requestModel) {
        String message = modifyAliment(requestModel.alimentName(), requestModel.newAlimentName(), requestModel.newAlimentDescription());
        outputPort.displayResponse(new ModifyAlimentResponseModel(message));
    }

    private String modifyAliment(String alimentName, String newAlimentName, String newAlimentDescription) {
        if(alimentName.isBlank()) {
            throw new DomainException("Aliment name must not be blank");
        }

        //Assure que l'aliment existe déjà
        Aliment aliment = alimentRepository.findAlimentByName(alimentName).orElseThrow(
                () -> new DomainException("Aliment " + alimentName + " does not exist")
        );

        //Assure que l'éventuel nouveau nom n'est pas déjà pris.
        if(!aliment.getName().equals(newAlimentName) && alimentRepository.findAlimentByName(newAlimentName).isPresent()) {
            throw new DomainException("new aliment name already exists");
        }

        //pas de modification de la valeur de isactive dans ce usecase
        Aliment modifiedAliment = new Aliment(newAlimentName, newAlimentDescription, aliment.isActive());
        if(modifiedAliment.check()) {
            //Modification
            try{
                alimentRepository.modify(aliment, newAlimentName, newAlimentDescription);
            } catch (Exception e) {
                throw new DomainException("Impossible to modify aliment " + aliment.getName());
            }
        }
        else{
            throw new DomainException("Invalid new aliment data");
        }

        return "Modification of aliment done";
    }
}
