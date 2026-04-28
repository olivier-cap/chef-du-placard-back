package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.util.Optional;

public interface IIngredientRepository {

    Optional<Ingredient> findCompleteById(Long id);
}
