package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockLineMapper{
    private final AlimentMapper alimentMapper;
    private final UnitMapper unitMapper;

    public StockLineMapper(AlimentMapper alimentMapper,
                           UnitMapper unitMapper){
        this.alimentMapper = alimentMapper;
        this.unitMapper = unitMapper;
    }

    public StockLine toDomain(StockLineJpa stockLineJpa) {
        Objects.requireNonNull(stockLineJpa, "stockLineJpa must not be null");
        Objects.requireNonNull(stockLineJpa.getAlimentJpa(), "alimentJPA must not be null");
        Objects.requireNonNull(stockLineJpa.getUnitJpa(), "unitJPA must not be null");

        return new StockLine(
                stockLineJpa.getQuantity(),
                alimentMapper.toDomain(stockLineJpa.getAlimentJpa()),
                unitMapper.toDomain(stockLineJpa.getUnitJpa())
        );
    }

    public StockLineJpa toEntity(StockLine stockLine) {
        Objects.requireNonNull(stockLine, "stockline must not be null");
        Objects.requireNonNull(stockLine.getAliment(), "aliment must not be null");
        Objects.requireNonNull(stockLine.getUnit(), "unit must not be null");

        return new StockLineJpa(
                alimentMapper.toEntity(stockLine.getAliment()),
                unitMapper.toEntity(stockLine.getUnit()),
                stockLine.getQuantity()
        );
    }
}
