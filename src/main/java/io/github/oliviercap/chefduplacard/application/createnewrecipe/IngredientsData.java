package io.github.oliviercap.chefduplacard.application.createnewrecipe;

import java.math.BigDecimal;

public record IngredientsData(Long alimentId, Long unitId, BigDecimal quantity) {
}
