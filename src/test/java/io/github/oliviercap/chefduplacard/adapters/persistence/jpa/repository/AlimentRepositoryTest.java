package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AlimentRepositoryTest {

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Test
    void save_and_load_aliment_entity() {
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);

        alimentJpaRepository.save(apple);

        var result = alimentJpaRepository.findByName("apple");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("apple");
        assertThat(result.get()).isEqualTo(apple);
    }
}