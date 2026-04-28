package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUnitJpaRepository extends JpaRepository<UnitJpa, UUID> {
    UnitJpa findUnitJpaByName(String name);
    UnitJpa findUnitJpaById(Long id);
}
