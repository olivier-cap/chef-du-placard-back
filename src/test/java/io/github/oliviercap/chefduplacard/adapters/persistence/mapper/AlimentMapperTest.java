package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlimentMapperTest {

    //Test transformation alimentJpa -> aliment domain
    @Test
    void creates_domain_aliment_from_jpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name","description", true);

        AlimentMapper alimentMapper = new AlimentMapper();

        Aliment alimentResult = alimentMapper.toDomain(alimentJpa);
        Aliment alimentExpected = new Aliment("name", "description", true);

        assertThat(alimentResult).isEqualTo(alimentExpected);

    }
}
