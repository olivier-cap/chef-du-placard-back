package io.github.oliviercap.chefduplacard.application.modifyaliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.ModifyAlimentViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.presenters.ModifyAlimentPresenter;
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
class ModifyAlimentUseCaseIntegrationTest {

    @Autowired
    private ModifyAlimentUseCase useCase;

    @Autowired
    private ModifyAlimentPresenter presenter;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Test
    void should_modify_existing_aliment_name_and_description_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        String oldAlimentName = "integration-modify-old-apple";
        String newAlimentName = "integration-modify-new-apple";

        AlimentJpa existing = new AlimentJpa(
                oldAlimentName,
                "old description",
                true
        );

        alimentJpaRepository.save(existing);

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        oldAlimentName,
                        newAlimentName,
                        "new description"
                );

        // ===== WHEN =====

        useCase.execute(request);

        ModifyAlimentViewModel result = presenter.getViewModel();

        // ===== THEN : presenter =====

        assertThat(result)
                .isNotNull();

        assertThat(result.message())
                .isEqualTo("Modification of aliment done");

        // ===== THEN : persistence réelle =====

        assertThat(alimentJpaRepository.findByName(oldAlimentName))
                .isEmpty();

        AlimentJpa modified = alimentJpaRepository.findByName(newAlimentName)
                .orElseThrow();

        assertThat(modified.getName())
                .isEqualTo(newAlimentName);

        assertThat(modified.getDescription())
                .isEqualTo("new description");

        assertThat(modified.isActive())
                .isTrue();
    }

    @Test
    void should_modify_existing_aliment_description_without_changing_name() {

        // ===== GIVEN =====

        String alimentName = "integration-modify-description-only-apple";

        AlimentJpa existing = new AlimentJpa(
                alimentName,
                "old description",
                true
        );

        alimentJpaRepository.save(existing);

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        alimentName,
                        new String(alimentName),
                        "new description"
                );

        // ===== WHEN =====

        useCase.execute(request);

        ModifyAlimentViewModel result = presenter.getViewModel();

        // ===== THEN : presenter =====

        assertThat(result)
                .isNotNull();

        assertThat(result.message())
                .isEqualTo("Modification of aliment done");

        // ===== THEN : persistence réelle =====

        AlimentJpa modified = alimentJpaRepository.findByName(alimentName)
                .orElseThrow();

        assertThat(modified.getName())
                .isEqualTo(alimentName);

        assertThat(modified.getDescription())
                .isEqualTo("new description");

        assertThat(modified.isActive())
                .isTrue();
    }

    @Test
    void should_throw_domain_exception_when_current_aliment_name_is_blank() {

        // ===== GIVEN =====

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        " ",
                        "new-name",
                        "new description"
                );

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Aliment name must not be blank");
    }

    @Test
    void should_throw_domain_exception_when_aliment_does_not_exist() {

        // ===== GIVEN =====

        String unknownAlimentName = "integration-unknown-aliment";

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        unknownAlimentName,
                        "new-name",
                        "new description"
                );

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Aliment " + unknownAlimentName + " does not exist");
    }

    @Test
    void should_throw_domain_exception_when_new_aliment_name_already_exists() {

        // ===== GIVEN =====

        String currentAlimentName = "integration-current-apple";
        String alreadyUsedAlimentName = "integration-existing-banana";

        AlimentJpa current = new AlimentJpa(
                currentAlimentName,
                "apple description",
                true
        );

        AlimentJpa alreadyExisting = new AlimentJpa(
                alreadyUsedAlimentName,
                "banana description",
                true
        );

        alimentJpaRepository.save(current);
        alimentJpaRepository.save(alreadyExisting);

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        currentAlimentName,
                        alreadyUsedAlimentName,
                        "new description"
                );

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("new aliment name already exists");

        // ===== THEN : persistence inchangée =====

        AlimentJpa unchangedCurrent = alimentJpaRepository.findByName(currentAlimentName)
                .orElseThrow();

        AlimentJpa unchangedExisting = alimentJpaRepository.findByName(alreadyUsedAlimentName)
                .orElseThrow();

        assertThat(unchangedCurrent.getDescription())
                .isEqualTo("apple description");

        assertThat(unchangedExisting.getDescription())
                .isEqualTo("banana description");
    }

    @Test
    void should_throw_domain_exception_when_new_aliment_name_is_blank() {

        // ===== GIVEN =====

        String alimentName = "integration-invalid-new-name-apple";

        AlimentJpa existing = new AlimentJpa(
                alimentName,
                "old description",
                true
        );

        alimentJpaRepository.save(existing);

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        alimentName,
                        "",
                        "new description"
                );

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Aliment name must not be null or blank");
    }
}
