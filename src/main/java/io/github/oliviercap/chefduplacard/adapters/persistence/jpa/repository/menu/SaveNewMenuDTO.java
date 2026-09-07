package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu;

import java.math.BigDecimal;
import java.util.List;

public record SaveNewMenuDTO(
        String menuName,
        List<SaveNewMenuLine> menuLines,
        Long userId
) {
    public record SaveNewMenuLine(Long recipeId, BigDecimal nbPerson) {}
}
