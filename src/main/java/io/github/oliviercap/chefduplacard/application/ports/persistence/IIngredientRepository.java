package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.util.Optional;

public interface IIngredientRepository {

    Optional<Ingredient> findCompleteById(Long id);
}
