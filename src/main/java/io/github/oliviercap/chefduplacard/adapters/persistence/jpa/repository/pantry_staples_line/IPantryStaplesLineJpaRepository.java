package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples_line;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesLineJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPantryStaplesLineJpaRepository extends JpaRepository<PantryStaplesLineJpa, Long> {


    @Query("""
    select distinct line
    from PantryStaplesLineJpa line
    left join fetch line.unitJpa
    left join fetch line.alimentJpa
    where line.id = :id
    """)
    Optional<PantryStaplesLineJpa> findCompleteById(@Param("id") Long id);
}
