package io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.controllers;

import java.math.BigDecimal;
import java.util.List;

public record SaveNewMenuRequest(
        String menuName,
        List<MenuLine> menuLines
) {
    public record MenuLine(
            BigDecimal nbPerson,
            Long recipeId
    ) {}
}
