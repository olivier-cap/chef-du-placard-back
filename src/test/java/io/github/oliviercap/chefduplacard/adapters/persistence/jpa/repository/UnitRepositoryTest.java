package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UnitRepositoryTest {

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void save_and_load_unit(){
        UnitJpa unitJpa = new UnitJpa("name","symbol");

        unitJpaRepository.save(unitJpa);

        var result = unitJpaRepository.findUnitJpaById(unitJpa.getId());


        assertThat(result.orElseThrow()).isEqualTo(unitJpa);

    }
}
