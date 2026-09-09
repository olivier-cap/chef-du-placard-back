package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.findByUser;

import java.math.BigDecimal;
import java.util.List;

public record FindByUserPantryStaplesViewModel(
        Long pantryStapleId,
        boolean is_default,
        List<PantryStapleLine> pantryStapleLines
) {
    public record PantryStapleLine(
            Long pantryStrapleLineId,
            String alimentName,
            String unitCode,
            BigDecimal quantity
    ) {}
}
