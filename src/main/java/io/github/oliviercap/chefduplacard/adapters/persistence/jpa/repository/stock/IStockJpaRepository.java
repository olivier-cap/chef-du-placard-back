package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * JPA Interface : used to totally separate domain and SpringBoot
 */
public interface IStockJpaRepository extends JpaRepository<StockJpa, Long> {
    @Query("""
            select distinct s
            from StockJpa s
            left join fetch s.stockLineJpa sl
                left join fetch sl.alimentJpa
                left join fetch sl.unitJpa
            where s.id =:id
            """)
    Optional<StockJpa> findCompleteById(@Param("id") Long id);


    @Query("""
            select distinct s
            from StockJpa s
            left join fetch s.stockLineJpa sl
                left join fetch sl.alimentJpa a
                left join fetch sl.unitJpa
            where s.id = :stockId
            and sl.id in :stockLineIds
            """)
    Optional<StockJpa> findStockForUpdate(@Param("stockId") Long stockId,@Param("stockLineIds") List<Long> stockLineIds);


}
