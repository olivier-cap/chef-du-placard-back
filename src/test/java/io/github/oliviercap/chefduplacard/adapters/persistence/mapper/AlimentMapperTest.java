package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlimentMapperTest {

    @Test
    void creates_domain_aliment_from_jpa() {
        AlimentJpa alimentJpa = new AlimentJpa(
                1L,
                "name",
                "description",
                true
        );

        AlimentMapper alimentMapper = new AlimentMapper();

        Aliment result = alimentMapper.toDomain(alimentJpa);

        assertThat(result.getId().id()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getDescription()).isEqualTo("description");
        assertThat(result.isActive()).isTrue();
    }

    @Test
    void creates_jpa_entity_from_domain_aliment() {
        Aliment aliment = new Aliment(
                new AlimentId(1L),
                "name",
                "description",
                true
        );

        AlimentMapper alimentMapper = new AlimentMapper();

        AlimentJpa result = alimentMapper.toEntity(aliment);

        assertThat(result.getId()).isNull();
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getDescription()).isEqualTo("description");
        assertThat(result.isActive()).isTrue();
    }
}