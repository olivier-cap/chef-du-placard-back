package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.dto;

import java.math.BigDecimal;

public record IngredientForPresenter(BigDecimal quantityPerPerson, String alimentName, String unitSymbol) {
}