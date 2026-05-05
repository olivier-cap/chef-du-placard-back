package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitMapperTest {

    //Test transformation from unit dto to unit domain
    @Test
    void create_unit_domain_from_dto(){
        UnitDTO unitDTO = new UnitDTO("name", "symbol");
        Unit unitExpected = new Unit("name","symbol");

        IUnitMapper unitMapper = new UnitMapper();

        assertThat(unitMapper.toDomain(unitDTO).getName()).isEqualTo(unitExpected.getName());
        assertThat(unitMapper.toDomain(unitDTO)).isEqualTo(unitExpected);

    }
}
