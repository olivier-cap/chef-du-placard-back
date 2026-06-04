package io.github.oliviercap.chefduplacard.adapters.web.savenewmenu;

import java.math.BigDecimal;
import java.util.List;

public record UpdateStockRequest(
        String menuName,
        List<MenuLine> menuLines
) {
    public record MenuLine(
            BigDecimal nbPerson,
            String recipeName
    ) {}
}
