package io.github.oliviercap.chefduplacard.adapters.web.unit.get_all_units;

import java.util.List;

public record GetAllUnitsViewModel(
        List<Unit> units
) {
    public record Unit(Long id,
                       String symbol,
                       String name) {
    }
}
