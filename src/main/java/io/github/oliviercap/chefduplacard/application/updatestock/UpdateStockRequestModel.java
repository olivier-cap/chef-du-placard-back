package io.github.oliviercap.chefduplacard.application.updatestock;

public record UpdateStockRequestModel(Long stockId, Long recipeId, int nbPeople) {
}
