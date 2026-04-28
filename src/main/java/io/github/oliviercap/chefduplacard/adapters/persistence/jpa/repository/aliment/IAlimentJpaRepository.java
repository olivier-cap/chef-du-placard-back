package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAlimentJpaRepository extends JpaRepository<AlimentJpa, UUID> {
    List<AlimentJpa> findAll();
    Optional<AlimentJpa> findByName(String name);
}
