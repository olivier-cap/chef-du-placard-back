package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.UpdateStockDTO;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.user.User;

import java.util.Optional;


public interface IStockRepository {
    Optional<Stock> findById(Long id);
    void save(Stock stock, User user);
    Stock updateStock(UpdateStockDTO updateStockDTO);

}
