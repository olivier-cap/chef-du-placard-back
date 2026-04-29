package io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.springframework.stereotype.Component;

/**
 * Helper to transform a Unit JPA Entity into DTO
 */
@Component
public class UnitJpaToDtoConverter implements IUnitJpaToDtoConverter{
    public UnitJpaToDtoConverter() {
    }

    public UnitDTO toDTO(UnitJpa unitJpa) {

        return new UnitDTO(unitJpa.getName(), unitJpa.getSymbol());
    }
}
