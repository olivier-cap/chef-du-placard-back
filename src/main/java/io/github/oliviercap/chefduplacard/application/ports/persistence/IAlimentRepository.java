package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

import java.util.List;
import java.util.Optional;

public interface IAlimentRepository {
    List<Aliment> findAll();
    Optional<Aliment> findAlimentByName(String name);
    List<AlimentJpa> findAllJpa();
    void save(Aliment newAliment);
    void modify(Aliment aliment, String newAlimentName, String newAlimentDescription);
    Optional<AlimentJpa>  findByName(String name);
    AlimentJpa getReferenceById(Long id);
    Optional<AlimentJpa> findAlimentJpaById(Long id);
}
