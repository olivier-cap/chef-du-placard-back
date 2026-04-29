package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.Optional;


public interface IStockRepository {
    public Optional<Stock> findByName(String name);
}
