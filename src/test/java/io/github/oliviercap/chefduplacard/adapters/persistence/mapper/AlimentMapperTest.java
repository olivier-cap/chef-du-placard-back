package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlimentMapperTest {

    //Test transformation alimentDto -> aliment domain
    @Test
    void creates_domain_aliment_from_dto(){
        AlimentDTO alimentDTO = new AlimentDTO("name","description", true);

        IAlimentMapper alimentMapper = new AlimentMapper();

        Aliment alimentResult = alimentMapper.toDomain(alimentDTO);
        Aliment alimentExpected = new Aliment("name", "description", true);

        assertThat(alimentResult).isEqualTo(alimentExpected);

    }
}
