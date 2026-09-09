package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;

import java.util.Optional;

public interface IPantryStaplesRepository {
    PantryStaples findByUserId(Long userId);
    Optional<PantryStaples> findBydId(Long pantryStaplesId);
}
