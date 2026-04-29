package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stockline;

import io.github.oliviercap.chefduplacard.domain.stock.StockLine;

import java.util.List;

public interface IStockLineRepository {
    List<StockLine> findAllComplete();
}
