package io.github.oliviercap.chefduplacard.application.getstock;

import java.util.List;

public record GetStockResponseModel(List<GetStockQuery> getStockQueryList) {
}
