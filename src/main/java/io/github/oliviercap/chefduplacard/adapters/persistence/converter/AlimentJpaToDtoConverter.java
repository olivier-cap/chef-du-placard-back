package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlimentJpaToDtoConverter implements IAlimentJpaToDtoConverter{

    public AlimentJpaToDtoConverter() {
    }

    public AlimentDTO toDTO(AlimentJpa alimentJpa){
        return new AlimentDTO(alimentJpa.getName(),
                alimentJpa.getDescription(),
                alimentJpa.isActive());
    }
}
