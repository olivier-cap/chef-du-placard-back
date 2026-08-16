package io.github.oliviercap.chefduplacard.application.modifyaliment;

public record ModifyAlimentRequestModel(Long alimentId, String newAlimentName, String newAlimentDescription) {
}
