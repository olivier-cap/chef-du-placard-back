package io.github.oliviercap.chefduplacard.application.unit.get_all_units;

import io.github.oliviercap.chefduplacard.application.htttpresponse.UnitResponse;

import java.util.List;

public record GetAllUnitsResponseModel(List<UnitResponse> responses) {
}
