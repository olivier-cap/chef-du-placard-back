package io.github.oliviercap.chefduplacard.application.getaliments;

import io.github.oliviercap.chefduplacard.application.htttpresponse.AlimentResponse;

import java.util.List;

public record GetAlimentsResponseModel(List<AlimentResponse> alimentResponses) {
}
