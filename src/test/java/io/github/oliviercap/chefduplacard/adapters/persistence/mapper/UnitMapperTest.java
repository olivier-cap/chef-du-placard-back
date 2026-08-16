package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitMapperTest {

    @Test
    void creates_domain_unit_from_jpa() {
        UnitJpa unitJpa = new UnitJpa(
                1L,
                "name",
                "symbol"
        );

        UnitMapper unitMapper = new UnitMapper();

        Unit result = unitMapper.toDomain(unitJpa);

        assertThat(result.getId().id()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getSymbol()).isEqualTo("symbol");
    }

    @Test
    void creates_jpa_entity_from_domain_unit() {
        Unit unit = new Unit(
                new UnitId(1L),
                "name",
                "symbol"
        );

        UnitMapper unitMapper = new UnitMapper();

        UnitJpa result = unitMapper.toEntity(unit);

        assertThat(result.getId()).isNull();
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getSymbol()).isEqualTo("symbol");
    }
}