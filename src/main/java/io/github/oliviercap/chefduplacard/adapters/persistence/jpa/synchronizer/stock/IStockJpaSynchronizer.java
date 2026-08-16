package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.Map;

public interface IStockJpaSynchronizer {

    void synchronize(StockJpa stockJpa,
                            Stock newStock,
                            Map<Long, AlimentJpa> existingAliment,
                            Map<Long, UnitJpa> existingUnit);
}
