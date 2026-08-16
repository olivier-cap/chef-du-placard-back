package io.github.oliviercap.chefduplacard.application.updatestockmanually;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
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
        // Given: aliments and unit already existing in database
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-manual-stock-apple",
                        "fruit",
                        true
                )
        );

        AlimentJpa banana = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-manual-stock-banana",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-manual-stock",
                        "g-manual-stock"
                )
        );

        // Given: stock and stock lines already existing in database
        StockJpa stock = new StockJpa("integration-manual-stock");

        StockLineJpa appleLine = new StockLineJpa(
                apple,
                gram,
                BigDecimal.ONE
        );

        StockLineJpa bananaLine = new StockLineJpa(
                banana,
                gram,
                BigDecimal.ONE
        );

        stock.addStockLine(appleLine);
        stock.addStockLine(bananaLine);

        StockJpa savedStock = stockJpaRepository.save(stock);

        UpdateStockManuallyRequestModel request =
                new UpdateStockManuallyRequestModel(
                        savedStock.getId(),
                        List.of(
                                new UpdateStockManuallyRequestModel.UpdateStockAliment(
                                        appleLine.getId(),
                                        BigDecimal.valueOf(10),
                                        gram.getId()
                                ),
                                new UpdateStockManuallyRequestModel.UpdateStockAliment(
                                        bananaLine.getId(),
                                        BigDecimal.valueOf(5),
                                        gram.getId()
                                )
                        )
                );

        // When
        useCase.execute(request);

        UpdateStockManuallyViewModel result = presenter.getViewModel();

        // Then: presenter
        assertThat(result.stockSaved()).isTrue();
        assertThat(result.responseMessage()).isEqualTo("Stock saved");

        // Then: real persistence
        StockJpa updatedStock = stockJpaRepository
                .findCompleteById(savedStock.getId())
                .orElseThrow();

        assertThat(updatedStock.getStockLineJpa()).hasSize(2);

        assertThat(updatedStock.getStockLineJpa())
                .extracting(line -> line.getAlimentJpa().getName())
                .containsExactlyInAnyOrder(
                        "integration-manual-stock-apple",
                        "integration-manual-stock-banana"
                );

        assertThat(updatedStock.getStockLineJpa())
                .filteredOn(line -> line.getAlimentJpa()
                        .getName()
                        .equals("integration-manual-stock-apple"))
                .singleElement()
                .extracting(StockLineJpa::getQuantity)
                .isEqualTo(BigDecimal.valueOf(10));

        assertThat(updatedStock.getStockLineJpa())
                .filteredOn(line -> line.getAlimentJpa()
                        .getName()
                        .equals("integration-manual-stock-banana"))
                .singleElement()
                .extracting(StockLineJpa::getQuantity)
                .isEqualTo(BigDecimal.valueOf(5));
    }
}