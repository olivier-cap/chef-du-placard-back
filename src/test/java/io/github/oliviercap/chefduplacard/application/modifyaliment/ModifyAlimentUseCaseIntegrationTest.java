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
        // Given
        String oldAlimentName = "integration-modify-old-apple";
        String newAlimentName = "integration-modify-new-apple";

        AlimentJpa savedAliment = alimentJpaRepository.save(
                new AlimentJpa(
                        oldAlimentName,
                        "old description",
                        true
                )
        );

        Long alimentId = savedAliment.getId();

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        alimentId,
                        newAlimentName,
                        "new description"
                );

        // When
        useCase.execute(request);

        ModifyAlimentViewModel result = presenter.getViewModel();

        // Then: presenter
        assertThat(result).isNotNull();
        assertThat(result.message())
                .isEqualTo("Modification of aliment done");

        // Then: real persistence
        AlimentJpa modified = alimentJpaRepository.findById(alimentId)
                .orElseThrow();

        assertThat(modified.getId()).isEqualTo(alimentId);
        assertThat(modified.getName()).isEqualTo(newAlimentName);
        assertThat(modified.getDescription()).isEqualTo("new description");
        assertThat(modified.isActive()).isTrue();
    }

    @Test
    void should_modify_existing_aliment_description_without_changing_name() {
        // Given
        String alimentName = "integration-modify-description-only-apple";

        AlimentJpa savedAliment = alimentJpaRepository.save(
                new AlimentJpa(
                        alimentName,
                        "old description",
                        true
                )
        );

        Long alimentId = savedAliment.getId();

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        alimentId,
                        new String(alimentName),
                        "new description"
                );

        // When
        useCase.execute(request);

        ModifyAlimentViewModel result = presenter.getViewModel();

        // Then: presenter
        assertThat(result).isNotNull();
        assertThat(result.message())
                .isEqualTo("Modification of aliment done");

        // Then: real persistence
        AlimentJpa modified = alimentJpaRepository.findById(alimentId)
                .orElseThrow();

        assertThat(modified.getId()).isEqualTo(alimentId);
        assertThat(modified.getName()).isEqualTo(alimentName);
        assertThat(modified.getDescription()).isEqualTo("new description");
        assertThat(modified.isActive()).isTrue();
    }

    @Test
    void should_throw_domain_exception_when_aliment_id_is_null() {
        // Given
        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        null,
                        "new-name",
                        "new description"
                );

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Aliment id must not be null");
    }

    @Test
    void should_throw_domain_exception_when_aliment_does_not_exist() {
        // Given
        Long unknownAlimentId = 999999L;

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        unknownAlimentId,
                        "new-name",
                        "new description"
                );

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage(
                        "Aliment " + unknownAlimentId + " does not exist"
                );
    }

    @Test
    void should_throw_domain_exception_when_new_aliment_name_already_exists() {
        // Given
        String currentAlimentName = "integration-current-apple";
        String alreadyUsedAlimentName = "integration-existing-banana";

        AlimentJpa currentAliment = alimentJpaRepository.save(
                new AlimentJpa(
                        currentAlimentName,
                        "apple description",
                        true
                )
        );

        AlimentJpa alreadyExistingAliment = alimentJpaRepository.save(
                new AlimentJpa(
                        alreadyUsedAlimentName,
                        "banana description",
                        true
                )
        );

        Long currentAlimentId = currentAliment.getId();
        Long alreadyExistingAlimentId = alreadyExistingAliment.getId();

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        currentAlimentId,
                        alreadyUsedAlimentName,
                        "new description"
                );

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("new aliment name already exists");

        // Then: persistence remains unchanged
        AlimentJpa unchangedCurrent = alimentJpaRepository
                .findById(currentAlimentId)
                .orElseThrow();

        AlimentJpa unchangedExisting = alimentJpaRepository
                .findById(alreadyExistingAlimentId)
                .orElseThrow();

        assertThat(unchangedCurrent.getName())
                .isEqualTo(currentAlimentName);
        assertThat(unchangedCurrent.getDescription())
                .isEqualTo("apple description");

        assertThat(unchangedExisting.getName())
                .isEqualTo(alreadyUsedAlimentName);
        assertThat(unchangedExisting.getDescription())
                .isEqualTo("banana description");
    }

    @Test
    void should_throw_domain_exception_when_new_aliment_name_is_blank() {
        // Given
        String alimentName = "integration-invalid-new-name-apple";

        AlimentJpa savedAliment = alimentJpaRepository.save(
                new AlimentJpa(
                        alimentName,
                        "old description",
                        true
                )
        );

        ModifyAlimentRequestModel request =
                new ModifyAlimentRequestModel(
                        savedAliment.getId(),
                        "",
                        "new description"
                );

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Aliment name must not be null or blank");

        // Then: persistence remains unchanged
        AlimentJpa unchanged = alimentJpaRepository
                .findById(savedAliment.getId())
                .orElseThrow();

        assertThat(unchanged.getName()).isEqualTo(alimentName);
        assertThat(unchanged.getDescription())
                .isEqualTo("old description");
    }
}