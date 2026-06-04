package io.github.oliviercap.chefduplacard.adapters.web.updatestock.controllers;

import java.math.BigDecimal;
import java.util.List;

public record NewMenuRecord(
        String menuName,
        List<MenuLine> menuLines
) {
    public record MenuLine(
            BigDecimal nbPerson,
            String recipeName
    ) {}
}
