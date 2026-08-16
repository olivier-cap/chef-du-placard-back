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
class AlimentJpaRepositoryTest {

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Test
    void save_and_load_aliment_entity() {
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);

        AlimentJpa savedApple = alimentJpaRepository.saveAndFlush(apple);

        assertThat(savedApple.getId()).isNotNull();

        AlimentJpa loadedApple = alimentJpaRepository
                .findById(savedApple.getId())
                .orElseThrow();

        assertThat(loadedApple.getId()).isEqualTo(savedApple.getId());
        assertThat(loadedApple.getName()).isEqualTo("apple");
        assertThat(loadedApple.getDescription()).isEqualTo("fruit");
        assertThat(loadedApple.isActive()).isTrue();
    }
}