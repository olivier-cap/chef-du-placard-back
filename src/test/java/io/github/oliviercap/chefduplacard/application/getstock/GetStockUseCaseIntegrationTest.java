package io.github.oliviercap.chefduplacard.application.getstock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.stock.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.stock.getstock.GetStockViewModel.StockLineViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.stock.getstock.presenters.GetStockPresenter;
import io.github.oliviercap.chefduplacard.application.stock.getstock.GetStockRequestModel;
import io.github.oliviercap.chefduplacard.application.stock.getstock.GetStockUseCase;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GetStockUseCaseIntegrationTest {

    @Autowired private GetStockUseCase useCase;
    @Autowired private GetStockPresenter presenter;
    @Autowired private IStockJpaRepository stockJpaRepository;
    @Autowired private IAlimentJpaRepository alimentJpaRepository;
    @Autowired private IUnitJpaRepository unitJpaRepository;
    @PersistenceContext private EntityManager entityManager;

    @Test
    void should_get_stock_with_real_persistence_pipeline() {
        AlimentJpa apple = alimentJpaRepository.save(new AlimentJpa(
                "integration-get-stock-apple", "fruit", true));
        UnitJpa gram = unitJpaRepository.save(new UnitJpa("gramme", "g"));

        UserJpa owner = new UserJpa(
                "get-stock-user", "get-stock@example.com", false);
        entityManager.persist(owner);

        StockJpa stock = new StockJpa("integration-test-stock", owner);
        stock.addStockLine(new StockLineJpa(
                apple, gram, BigDecimal.valueOf(20)));
        StockJpa savedStock = stockJpaRepository.save(stock);

        useCase.execute(new GetStockRequestModel(savedStock.getId()));

        GetStockViewModel result = presenter.getViewModel();

        assertThat(result).isNotNull();
        assertThat(result.stockLineViewModelList()).hasSize(1);
        StockLineViewModel line = result.stockLineViewModelList().getFirst();
        assertThat(line.alimentName()).isEqualTo("integration-get-stock-apple");
        assertThat(line.unitSymbol()).isEqualTo("g");
        assertThat(line.quantity()).isEqualByComparingTo(BigDecimal.valueOf(20));
    }

    @Test
    void should_throw_domain_exception_when_stock_id_is_null() {
        GetStockRequestModel request = new GetStockRequestModel(null);

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("stockid must not be null");
    }
}