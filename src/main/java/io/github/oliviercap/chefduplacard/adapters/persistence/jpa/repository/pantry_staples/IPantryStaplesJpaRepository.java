package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPantryStaplesJpaRepository extends JpaRepository<PantryStaplesJpa, Long> {


    @Query("""
            select distinct p
            from PantryStaplesJpa p
            left join fetch p.userJpa
            left join fetch p.pantryStaplesLineSet
            where p.userJpa.id = :userId
            """)
    Optional<PantryStaplesJpa> findByUserId(@Param("userId") Long userId);

    @Query("""
            select distinct p
            from PantryStaplesJpa p
            left join fetch p.userJpa
            left join fetch p.pantryStaplesLineSet line
            left join fetch line.unitJpa
            left join fetch line.alimentJpa al
            left join fetch al.alimentTypes
            where p.id = :id
            """)
    Optional<PantryStaplesJpa> findCompleteById(@Param("id") Long id);
}
