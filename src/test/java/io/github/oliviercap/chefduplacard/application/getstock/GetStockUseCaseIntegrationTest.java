package io.github.oliviercap.chefduplacard.application.getstock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getstock.GetStockViewModel.StockLineViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getstock.presenters.GetStockPresenter;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GetStockUseCaseIntegrationTest {

    @Autowired
    private GetStockUseCase useCase;

    @Autowired
    private GetStockPresenter presenter;

    @Autowired
    private IStockJpaRepository stockJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_get_stock_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa gram = new UnitJpa("gramme", "g");

        StockJpa stock = new StockJpa("test-stock");
        stock.addStockLine(new StockLineJpa(apple, gram, BigDecimal.valueOf(20)));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(gram);
        stockJpaRepository.save(stock);

        // ===== WHEN =====

        GetStockRequestModel request = new GetStockRequestModel("test-stock");

        useCase.execute(request);

        GetStockViewModel result = presenter.getViewModel();

        // ===== THEN =====

        assertThat(result).isNotNull();

        assertThat(result.stockLineViewModelList())
                .hasSize(1);

        StockLineViewModel line = result.stockLineViewModelList().getFirst();

        assertThat(line.alimentName())
                .isEqualTo("apple");

        assertThat(line.unitSymbol())
                .isEqualTo("g");

        assertThat(line.quantity())
                .isEqualByComparingTo(BigDecimal.valueOf(20));
    }

    @Test
    void should_throw_domain_exception_when_stock_name_is_blank() {

        // ===== GIVEN =====

        GetStockRequestModel request = new GetStockRequestModel("");

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("stock name must not be null");
    }
}