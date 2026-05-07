package io.github.oliviercap.chefduplacard.adapters.persistence.dto;

import java.util.List;

public record RecipeDTO(
        String name,
        String instructions,
        Integer duration,
        String difficulty,
        List<IngredientDTO> ingredients
) {}