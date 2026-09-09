package io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe;

import java.math.BigDecimal;

public record IngredientsData(Long alimentId, Long unitId, BigDecimal quantity) {
}
