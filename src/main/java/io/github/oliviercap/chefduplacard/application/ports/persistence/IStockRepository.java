package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.Optional;


public interface IStockRepository {
    public Optional<Stock> findByName(String name);

    public void save(Stock stock);
}
