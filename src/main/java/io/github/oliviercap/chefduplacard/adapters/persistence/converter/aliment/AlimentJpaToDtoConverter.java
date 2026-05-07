package io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Helper to transform an Aliment JPA Entity into DTO
 */
@Component
public class AlimentJpaToDtoConverter implements IAlimentJpaToDtoConverter{

    public AlimentJpaToDtoConverter() {
    }

    public AlimentDTO toDTO(AlimentJpa alimentJpa){
        Objects.requireNonNull(alimentJpa, "alimentJpa must not be null");

        return new AlimentDTO(alimentJpa.getName(),
                alimentJpa.getDescription(),
                alimentJpa.isActive());
    }
}
