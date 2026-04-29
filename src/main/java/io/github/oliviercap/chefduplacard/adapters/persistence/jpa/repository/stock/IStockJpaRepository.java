package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            where s.name =:name
            """)
    Optional<StockJpa> findCompleteByName(@Param("name") String name);
}
