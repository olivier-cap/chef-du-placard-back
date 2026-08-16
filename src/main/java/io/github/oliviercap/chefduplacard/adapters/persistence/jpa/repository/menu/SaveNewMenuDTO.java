package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu;

import java.math.BigDecimal;
import java.util.List;

public record SaveNewMenuDTO(
        String menuName,
        List<saveNewMenuLine> menuLines
) {
    public record saveNewMenuLine(Long recipeId, BigDecimal nbPerson) {}
}
