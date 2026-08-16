package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
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


    /**
     * Mets à jour le stock de la Base de Donnée en mettant à jour le stockJpa.
     * StockJpa est managed par jpa, et tous ses composants aussi → modification et suppressions prises en compte automatiquement
     * La nouvelle référence est le newStock : on met à jour la base avec les infos de ce nouveau stock
     * Les aliments et les unités doivent déjà exister dans la base
     */
    @Override
    public void synchronize(StockJpa stockJpa,
                            Stock newStock,
                            Map<Long, AlimentJpa> existingAliment,
                            Map<Long, UnitJpa> existingUnit)
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
        Map<Long, StockLineJpa> existing = new HashMap<>();

        for (StockLineJpa line : stockJpa.getStockLineJpa()) {
            existing.put(line.getAlimentJpa().getId(), line);
        }

        //Ajout des nouvelles lignes de stock et modification des lignes existantes
        //L'aliment et l'unité doivent déjà exister dans la base
        for (StockLine stockLine : newStock.getStockMap().values()) {
            Long id = stockLine.getAliment().getId().id();

            StockLineJpa lineJpa = existing.get(id);

            //aliment déjà dans l'ancien stock
            if (lineJpa != null) {
                lineJpa.setQuantity(stockLine.getQuantity());
            } else { //aliment absent de l'ancien stock → nouvelle ligne créée
                stockJpa.addStockLine(
                        new StockLineJpa(
                                existingAliment.get(id),
                                existingUnit.get(stockLine.getUnit().getId().id()),
                                stockLine.getQuantity()
                        )
                );
            }
        }


        //Suppression dans la base des lignes qui ne sont plus présentes dans le nouveau stock
        //Creation d'un set des nouveaux "nom d'aliment"
        Set<Long> newNames = newStock.getStockMap().values().stream()
                .map(line -> line.getAliment().getId().id())
                .collect(Collectors.toSet());

        //Suppression effective des lignes
        stockJpa.getStockLineJpa().removeIf(lineJpa ->
                !newNames.contains(lineJpa.getAlimentJpa().getId())
        );
    }
}
