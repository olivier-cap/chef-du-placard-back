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
        String message = modifyAliment(requestModel.alimentId(), requestModel.newAlimentName(), requestModel.newAlimentDescription());
        outputPort.displayResponse(new ModifyAlimentResponseModel(message));
    }

    private String modifyAliment(
            Long alimentId,
            String newAlimentName,
            String newAlimentDescription
    ) {
        if (alimentId == null) {
            throw new DomainException("Aliment id must not be null");
        }

        Aliment aliment = alimentRepository.findAlimentById(alimentId)
                .orElseThrow(() -> new DomainException(
                        "Aliment " + alimentId + " does not exist"
                ));

        Aliment modifiedAliment = new Aliment(
                aliment.getId(),
                newAlimentName,
                newAlimentDescription,
                aliment.isActive()
        );

        boolean nameHasChanged =
                !aliment.getName().equals(modifiedAliment.getName());

        if (nameHasChanged
                && alimentRepository.existsByName(modifiedAliment.getName())) {
            throw new DomainException("new aliment name already exists");
        }

        try {
            alimentRepository.modify(
                    aliment,
                    modifiedAliment.getName(),
                    modifiedAliment.getDescription()
            );
        } catch (Exception e) {
            throw new DomainException(
                    "Impossible to modify aliment " + aliment.getName()
            );
        }

        return "Modification of aliment done";
    }
}
