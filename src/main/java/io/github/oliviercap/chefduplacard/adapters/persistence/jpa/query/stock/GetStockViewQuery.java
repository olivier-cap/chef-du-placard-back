package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.query.stock;

import io.github.oliviercap.chefduplacard.application.getstock.GetStockQuery;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetStockViewQuery;
import org.springframework.stereotype.Repository;

@Repository
public class GetStockViewQuery implements IGetStockViewQuery {

    private final IGetStockJpaQuery getStockJpaQuery;

    public GetStockViewQuery(IGetStockJpaQuery getStockJpaQuery) {
        this.getStockJpaQuery = getStockJpaQuery;
    }

    @Override
    public GetStockQuery getStockQuery() {
        return null;
    }
}
