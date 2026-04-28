package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IIngredientJpaRepository extends JpaRepository<IngredientJpa, Long> {

    @Query("""
            select distinct r
            from IngredientJpa r
            join fetch r.alimentJpa
            join fetch r.unitJpa
            where r.id = :id
            """)
    Optional<IngredientJpa> findCompleteById(@Param("id") Long id);
}
