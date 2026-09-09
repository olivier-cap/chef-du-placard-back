package io.github.oliviercap.chefduplacard.application.aliment.modifyaliment;

public record ModifyAlimentRequestModel(Long alimentId, String newAlimentName, String newAlimentDescription) {
}
