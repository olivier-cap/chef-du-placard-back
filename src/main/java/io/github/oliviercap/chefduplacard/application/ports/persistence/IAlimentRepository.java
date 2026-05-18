package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;

import java.util.List;
import java.util.Optional;

public interface IAlimentRepository {
    List<Aliment> findAll();
    Optional<Aliment> findAlimentByName(String name);
}
