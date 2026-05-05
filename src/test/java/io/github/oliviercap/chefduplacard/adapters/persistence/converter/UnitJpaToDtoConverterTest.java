package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.UnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitJpaToDtoConverterTest {

    @Test
    void creates_unitDto_from_unitJpa() {
        UnitJpa unitJpa = new UnitJpa("name","symbol");
        IUnitJpaToDtoConverter unitJpaToDtoConverter = new UnitJpaToDtoConverter();

        UnitDTO unitDTOExpected = new UnitDTO("name","symbol");
        UnitDTO result = unitJpaToDtoConverter.toDTO(unitJpa);

        assertThat(result).isEqualTo(unitDTOExpected);
    }
}
