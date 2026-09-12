package io.github.oliviercap.chefduplacard.application.menu.savenewmenu;

import java.math.BigDecimal;
import java.util.List;

public record SaveNewMenuRequestModel(Long userId, String menuName, List<MenuLine> lines) {
    public record MenuLine(
            BigDecimal nbPerson,
            Long recipeId
    ) {}
}
