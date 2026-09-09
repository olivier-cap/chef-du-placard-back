package io.github.oliviercap.chefduplacard.application.stock.getstock;

import java.util.List;

public record GetStockResponseModel(List<GetStockQuery> getStockQueryList) {
}
