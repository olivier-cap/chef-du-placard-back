package io.github.oliviercap.chefduplacard.application.getstock;

import java.math.BigDecimal;

public record GetStockQuery(BigDecimal quantity,
                            String alimentName,
                            String unitSymbol) {
}
