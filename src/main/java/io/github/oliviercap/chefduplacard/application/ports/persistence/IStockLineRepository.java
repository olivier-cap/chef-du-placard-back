package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.stock.StockLine;

import java.util.List;

public interface IStockLineRepository {
    List<StockLine> findAllComplete();
}
