package io.github.oliviercap.chefduplacard.application.getaliments;

import io.github.oliviercap.chefduplacard.application.dto.AlimentResponse;

import java.util.List;

public record GetAlimentsResponseModel(List<AlimentResponse> alimentResponses) {
}
