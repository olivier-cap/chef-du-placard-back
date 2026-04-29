package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUnitJpaRepository extends JpaRepository<UnitJpa, Long> {
    UnitJpa findUnitJpaByName(String name);
    UnitJpa findUnitJpaById(Long id);
}
