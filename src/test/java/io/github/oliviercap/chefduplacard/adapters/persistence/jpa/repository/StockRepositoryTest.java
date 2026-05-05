package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stockline.IStockLineJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class StockRepositoryTest {

    @Autowired
    private IStockJpaRepository stockJpaRepository;
    @Autowired
    private IStockLineJpaRepository stockLineJpaRepository;
    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;
    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void save_and_load_stock() {
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa unit = new UnitJpa("gramme","g");
        StockJpa stock = new StockJpa("name", List.of());
        StockLineJpa stockLine = new StockLineJpa(stock, apple, unit, BigDecimal.valueOf(12));

        stock.setStockLineJpa(List.of(stockLine));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(unit);
        stockJpaRepository.save(stock);

        stockLineJpaRepository.save(stockLine);


        var result = stockJpaRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStockLineJpa().getFirst().getAlimentJpa().getName()).isEqualTo("apple");
        assertThat(result.getFirst()).isEqualTo(stock);
    }
}
