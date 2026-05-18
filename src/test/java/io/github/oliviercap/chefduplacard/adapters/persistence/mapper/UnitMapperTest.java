package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitMapperTest {

    //Test transformation from unit dto to unit domain
    @Test
    void create_unit_domain_from_jpa(){
        UnitJpa unitJpa = new UnitJpa("name", "symbol");
        Unit unitExpected = new Unit("name","symbol");

        UnitMapper unitMapper = new UnitMapper();

        assertThat(unitMapper.toDomain(unitJpa).getName()).isEqualTo(unitExpected.getName());
        assertThat(unitMapper.toDomain(unitJpa)).isEqualTo(unitExpected);
    }
}
