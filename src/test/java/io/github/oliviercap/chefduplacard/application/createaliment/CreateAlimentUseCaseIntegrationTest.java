package io.github.oliviercap.chefduplacard.application.createaliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.createaliment.CreateAlimentViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.createaliment.presenters.CreateAlimentPresenter;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreateAlimentUseCaseIntegrationTest {

    @Autowired
    private CreateAlimentUseCase useCase;

    @Autowired
    private CreateAlimentPresenter presenter;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Test
    void should_create_new_aliment_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        String alimentName = "integration-create-apple";

        CreateAlimentRequestModel request =
                new CreateAlimentRequestModel(
                        alimentName,
                        "fruit",
                        true
                );

        // ===== WHEN =====

        useCase.execute(request);

        CreateAlimentViewModel result = presenter.getViewModel();

        // ===== THEN : presenter =====

        assertThat(result)
                .isNotNull();

        assertThat(result.response())
                .isEqualTo("Aliment saved");

        // ===== THEN : persistence réelle =====

        AlimentJpa saved = alimentJpaRepository.findByName(alimentName)
                .orElseThrow();

        assertThat(saved.getName())
                .isEqualTo(alimentName);

        assertThat(saved.getDescription())
                .isEqualTo("fruit");

        assertThat(saved.isActive())
                .isTrue();
    }

    @Test
    void should_not_create_aliment_if_name_already_used() {

        // ===== GIVEN =====

        String alimentName = "integration-duplicate-apple";

        AlimentJpa existing = new AlimentJpa(alimentName, "fruit", true);
        alimentJpaRepository.save(existing);

        long countBefore = alimentJpaRepository.findAll()
                .stream()
                .filter(aliment -> aliment.getName().equals(alimentName))
                .count();

        CreateAlimentRequestModel request =
                new CreateAlimentRequestModel(
                        alimentName,
                        "fruit",
                        true
                );

        // ===== WHEN =====

        useCase.execute(request);

        CreateAlimentViewModel result = presenter.getViewModel();

        long countAfter = alimentJpaRepository.findAll()
                .stream()
                .filter(aliment -> aliment.getName().equals(alimentName))
                .count();

        // ===== THEN : presenter =====

        assertThat(result)
                .isNotNull();

        assertThat(result.response())
                .isEqualTo("Aliment name already used");

        // ===== THEN : pas de duplication en base =====

        assertThat(countBefore)
                .isEqualTo(1);

        assertThat(countAfter)
                .isEqualTo(countBefore);
    }

    @Test
    void should_throw_domain_exception_when_aliment_name_is_blank() {

        // ===== GIVEN =====

        CreateAlimentRequestModel request =
                new CreateAlimentRequestModel(
                        "",
                        "fruit",
                        true
                );

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Aliment name must not be null or blank");
    }
}