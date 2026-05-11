package io.github.oliviercap.chefduplacard.application.dto;

import java.math.BigDecimal;

public record IngredientResponse(
        BigDecimal quantityPerPerson,
        AlimentResponse alimentResponse,
        UnitResponse unitResponse
){}