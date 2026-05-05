package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.AlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlimentJpaToDtoConverterTest {

    @Test
    void creates_alimentDto_from_alimentJpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name", "description", true);
        IAlimentJpaToDtoConverter alimentJpaToDtoConverter = new AlimentJpaToDtoConverter();

        AlimentDTO alimentDTO = alimentJpaToDtoConverter.toDTO(alimentJpa);
        AlimentDTO expectedDTO = new AlimentDTO("name","description", true);

        assertThat(alimentDTO).isEqualTo(expectedDTO);

    }
}
