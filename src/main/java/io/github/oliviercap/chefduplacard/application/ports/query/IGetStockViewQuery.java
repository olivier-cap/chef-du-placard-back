package io.github.oliviercap.chefduplacard.application.ports.query;

import io.github.oliviercap.chefduplacard.application.stock.getstock.GetStockQuery;

import java.util.List;

public interface IGetStockViewQuery {

    List<GetStockQuery> getStockQuery(Long stockId);
}
