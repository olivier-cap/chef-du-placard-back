package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gestion de la sauvegarde d'un stock en utilisant les aliments et unites préexistantes en base
 * Permet la modification, l'ajout et la suppression de lignes de stock dans la base
 */
@Component
public class StockJpaSynchronizer implements IStockJpaSynchronizer {
    private final AlimentMapper alimentMapper;
    private final StockLineMapper stockLineMapper;

    public StockJpaSynchronizer(AlimentMapper alimentMapper,
                                StockLineMapper stockLineMapper
    ) {
        this.alimentMapper = alimentMapper;
        this.stockLineMapper = stockLineMapper;
    }


    @Override
    public void synchronize(StockJpa stockJpa,
                            Stock newStock,
                            Map<String, AlimentJpa> existingAliment,
                            Map<String, UnitJpa> existingUnit)
    {
        Objects.requireNonNull(stockJpa, "stockJpa must not be null");
        Objects.requireNonNull(newStock, "newStock must not be null");
        Objects.requireNonNull(existingAliment, "existingAliment must not be null");
        Objects.requireNonNull(existingUnit, "existingUnit must not be null");

        if(existingAliment.isEmpty()) {
            throw new DomainException("There is not Aliment in database");
        }

        if(existingUnit.isEmpty()) {
            throw new DomainException("There is no Unit in database");
        }

        //<alimentName stockLineWithThisAliment>
        //issus du stock précédent (dans la base de données)
        Map<String, StockLineJpa> existing = new HashMap<>();

        for (StockLineJpa line : stockJpa.getStockLineJpa()) {
            existing.put(line.getAlimentJpa().getName(), line);
        }

        //Ajout des nouvelles lignes de stock et modification des lignes existantes
        //L'aliment et l'unité doivent déjà exister dans la base
        for (StockLine stockLine : newStock.getStockMap().values()) {
            String name = stockLine.getAliment().getName();

            StockLineJpa lineJpa = existing.get(name);

            //aliment déjà dans l'ancien stock
            if (lineJpa != null) {
                lineJpa.setQuantity(stockLine.getQuantity());
            } else { //aliment absent de l'ancien stock → nouvelle ligne créée
                stockJpa.addStockLine(
                        new StockLineJpa(
                                existingAliment.get(name),
                                existingUnit.get(stockLine.getUnit().getName()),
                                stockLine.getQuantity()
                        )
                );
            }
        }


        //Suppression dans la base des lignes qui ne sont plus présentes dans le nouveau stock
        //Creation d'un set des nouveaux "nom d'aliment"
        Set<String> newNames = newStock.getStockMap().values().stream()
                .map(line -> line.getAliment().getName())
                .collect(Collectors.toSet());

        //Suppression effective des lignes
        stockJpa.getStockLineJpa().removeIf(lineJpa ->
                !newNames.contains(lineJpa.getAlimentJpa().getName())
        );
    }
}
