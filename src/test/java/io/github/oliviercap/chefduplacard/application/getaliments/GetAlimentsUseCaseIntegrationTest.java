package io.github.oliviercap.chefduplacard.application.getaliments;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.getaliments.GetAlimentsViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getaliments.GetAlimentsViewModel.AlimentViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getaliments.presenters.GetAlimentsPresenter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GetAlimentsUseCaseIntegrationTest {

    @Autowired
    private GetAlimentsUseCase useCase;

    @Autowired
    private GetAlimentsPresenter presenter;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Test
    void should_get_aliments_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa(
                "integration-apple",
                "fruit",
                true
        );

        AlimentJpa oldBanana = new AlimentJpa(
                "integration-old-banana",
                "old fruit",
                false
        );

        alimentJpaRepository.save(apple);
        alimentJpaRepository.save(oldBanana);

        // ===== WHEN =====

        GetAlimentsRequestModel request = new GetAlimentsRequestModel();

        useCase.execute(request);

        GetAlimentsViewModel result = presenter.getViewModel();

        // ===== THEN =====

        assertThat(result)
                .isNotNull();

        assertThat(result.alimentViewModelList())
                .isNotNull();

        assertThat(result.alimentViewModelList())
                .extracting(
                        AlimentViewModel::alimentName,
                        AlimentViewModel::alimentDescription,
                        AlimentViewModel::isActive
                )
                .contains(
                        tuple("integration-apple", "fruit", true),
                        tuple("integration-old-banana", "old fruit", false)
                );
    }
}