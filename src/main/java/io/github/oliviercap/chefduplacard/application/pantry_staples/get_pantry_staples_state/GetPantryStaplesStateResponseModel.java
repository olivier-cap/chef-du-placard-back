package io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state;

import io.github.oliviercap.chefduplacard.application.htttpresponse.PantryStaplesLineResponse;

import java.math.BigDecimal;
import java.util.List;

public record GetPantryStaplesStateResponseModel(
        List<StapleStateLine> stateLineList
) {
    public record StapleStateLine(
            PantryStaplesLineResponse pantryStaplesLineResponse,
            BigDecimal actualQuantity,
            boolean isSufficient
    ){}
}
