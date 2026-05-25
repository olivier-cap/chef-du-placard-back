package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.AlimentRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock.IStockJpaSynchronizer;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.StockMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stock Repository.
 * Purpose functions to access data in database about stock.
 */
@Repository
public class StockRepository implements IStockRepository {
    private final IStockJpaRepository stockJpaRepository;
    private final IStockJpaSynchronizer stockJpaSynchronizer;
    private final StockMapper stockMapper;
    private final IAlimentRepository alimentRepository;
    private final IUnitRepository unitRepository;

    public StockRepository(IStockJpaRepository stockJpaRepository,
                           IStockJpaSynchronizer stockJpaSynchronizer,
                           StockMapper stockMapper,
                           AlimentRepository alimentRepository,
                           IUnitRepository unitRepository) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockJpaSynchronizer = stockJpaSynchronizer;
        this.stockMapper = stockMapper;
        this.alimentRepository = alimentRepository;
        this.unitRepository = unitRepository;
    }

    /**
     * Find a particular stock by its name.
     * Creates all the chain : all stocklines, aliments et units are created
     * And linked to the stock
     * @param name name of the required stock
     * @return Stock
     */
    @Override
    public Optional<Stock> findByName(String name) {
        return stockJpaRepository.findCompleteByName(name)
                .map(stockMapper::toDomain);
    }

    /**
     * Sauvegarde un stock dans la base.
     * Ajoute, supprime et modifie les stockLines pour correspondre au nouveau stock.
     * Récupère les aliments et unités déjà existantes : pas de modification sur les aliments et unités
     * @param stock stock à sauvegarder
     */
    @Override
    @Transactional
    public void save(Stock stock) {

        //Recherche du stock dans la base. Permet à JPA de gérer le stock, le modifier.
        //Création d'un nouveau stock s'il n'existe pas en base
        StockJpa stockJpa = stockJpaRepository.findCompleteByName(stock.getName())
                .orElseGet(() -> new StockJpa(stock.getName()));

        List<AlimentJpa> existingAliments = alimentRepository.findAllJpa();
        List<UnitJpa> existingUnits = unitRepository.findAllJpa();

        Map<String, AlimentJpa> alimentJpaMap = new HashMap<>();
        Map<String, UnitJpa> unitJpaMap = new HashMap<>();

        for(AlimentJpa aliment: existingAliments){ alimentJpaMap.put(aliment.getName(), aliment); }
        for(UnitJpa unit : existingUnits) { unitJpaMap.put(unit.getName(), unit); }

        stockJpaSynchronizer.synchronize(stockJpa, stock, alimentJpaMap, unitJpaMap);

        //redondant pour les stocks existant mais nécessaire pour les nouveaux stocks
        stockJpaRepository.save(stockJpa);
    }


}
