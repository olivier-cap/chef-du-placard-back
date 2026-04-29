package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStockLineJpaRepository extends JpaRepository<StockLineJpa, Long> {
    @Query("""
            select distinct j
            from StockLineJpa j
            join fetch j.unitJpa
            join fetch j.alimentJpa
            """)
    List<StockLineJpa> findAllComplete();
}
