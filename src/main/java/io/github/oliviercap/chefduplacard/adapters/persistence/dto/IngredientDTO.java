package io.github.oliviercap.chefduplacard.adapters.persistence.dto;


import java.math.BigDecimal;

public record IngredientDTO(BigDecimal quantityPerPerson, AlimentDTO alimentDTO, UnitDTO unitDTO) {
}
