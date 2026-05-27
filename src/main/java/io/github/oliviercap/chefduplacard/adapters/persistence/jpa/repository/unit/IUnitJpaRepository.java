package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IUnitJpaRepository extends JpaRepository<UnitJpa, Long> {
    Optional<UnitJpa> findUnitJpaByName(String name);
    Optional<UnitJpa> findUnitJpaById(Long id);
}
