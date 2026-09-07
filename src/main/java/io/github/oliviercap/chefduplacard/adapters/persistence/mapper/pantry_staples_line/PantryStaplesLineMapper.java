package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples_line;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLineId;
import org.springframework.stereotype.Component;

@Component
public class PantryStaplesLineMapper {

    private final AlimentMapper alimentMapper;
    private final UnitMapper unitMapper;

    public PantryStaplesLineMapper(AlimentMapper alimentMapper, UnitMapper unitMapper) {
        this.alimentMapper = alimentMapper;
        this.unitMapper = unitMapper;
    }

    public PantryStaplesLine toDomain(PantryStaplesLineJpa pantryStrapleLineJpa) {
        return new PantryStaplesLine(
                new PantryStaplesLineId(
                        pantryStrapleLineJpa.getId()),
                        alimentMapper.toDomain(pantryStrapleLineJpa.getAlimentJpa()),
                        unitMapper.toDomain(pantryStrapleLineJpa.getUnitJpa()),
                        pantryStrapleLineJpa.getQuantity()
        );
    }
}
