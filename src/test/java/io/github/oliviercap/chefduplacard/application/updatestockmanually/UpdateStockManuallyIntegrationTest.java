package io.github.oliviercap.chefduplacard.application.updatestockmanually;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.UpdateStockManuallyViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.presenters.UpdateStockManuallyPresenter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UpdateStockManuallyIntegrationTest {

    @Autowired
    private UpdateStockManuallyUseCase useCase;

    @Autowired
    private UpdateStockManuallyPresenter presenter;

    @Autowired
    private IStockJpaRepository stockJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_replace_stock_with_user_defined_values() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        AlimentJpa banana = new AlimentJpa("banana", "fruit", true);

        UnitJpa gram = new UnitJpa("gramme", "g");

        alimentJpaRepository.save(apple);
        alimentJpaRepository.save(banana);
        unitJpaRepository.save(gram);

        UpdateStockManuallyRequestModel request =
                new UpdateStockManuallyRequestModel(
                        "test-stock",
                        List.of(
                                new UpdateStockManuallyRequestModel.UpdateStockAliment(
                                        "apple",
                                        BigDecimal.valueOf(10),
                                        "gramme"
                                ),
                                new UpdateStockManuallyRequestModel.UpdateStockAliment(
                                        "banana",
                                        BigDecimal.valueOf(5),
                                        "gramme"

                                )
                        )
                );

        // ===== WHEN =====

        useCase.execute(request);

        UpdateStockManuallyViewModel result = presenter.getViewModel();

        // ===== THEN : presenter =====

        assertThat(result.stockSaved()).isTrue();
        assertThat(result.responseMessage())
                .isEqualTo("Stock saved");

        // ===== THEN : persistence réelle =====

        StockJpa savedStock = stockJpaRepository.findCompleteByName("test-stock")
                .orElseThrow();

        assertThat(savedStock.getStockLineJpa())
                .hasSize(2);

        assertThat(savedStock.getStockLineJpa())
                .extracting(line -> line.getAlimentJpa().getName())
                .containsExactlyInAnyOrder("apple", "banana");

        assertThat(savedStock.getStockLineJpa())
                .filteredOn(line -> line.getAlimentJpa().getName().equals("apple"))
                .first()
                .extracting(line -> line.getQuantity())
                .isEqualTo(BigDecimal.valueOf(10));

        assertThat(savedStock.getStockLineJpa())
                .filteredOn(line -> line.getAlimentJpa().getName().equals("banana"))
                .first()
                .extracting(line -> line.getQuantity())
                .isEqualTo(BigDecimal.valueOf(5));
    }
}