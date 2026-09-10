package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment_type.AlimentTypeMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlimentMapperTest {

    private final AlimentMapper alimentMapper = new AlimentMapper(
            new AlimentTypeMapper()
    );

    @Test
    void creates_domain_aliment_from_jpa() {
        AlimentJpa alimentJpa = new AlimentJpa(
                1L,
                "name",
                "description",
                true
        );

        Aliment result = alimentMapper.toDomain(alimentJpa);

        assertThat(result.getId().id()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getDescription()).isEqualTo("description");
        assertThat(result.isActive()).isTrue();
    }


}