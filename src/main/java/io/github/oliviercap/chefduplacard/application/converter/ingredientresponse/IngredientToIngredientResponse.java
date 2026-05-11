package io.github.oliviercap.chefduplacard.application.converter.ingredientresponse;

import io.github.oliviercap.chefduplacard.application.converter.alimentresponse.IAlimentToAlimentResponse;
import io.github.oliviercap.chefduplacard.application.converter.unitresponse.IUnitToUnitResponse;
import io.github.oliviercap.chefduplacard.application.dto.IngredientResponse;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class IngredientToIngredientResponse implements IIngredientToIngredientResponse {

    private final IAlimentToAlimentResponse alimentResponse;
    private final IUnitToUnitResponse unitResponse;

    public IngredientToIngredientResponse(IAlimentToAlimentResponse alimentResponse, IUnitToUnitResponse unitResponse) {
        this.alimentResponse = alimentResponse;
        this.unitResponse = unitResponse;
    }

    public IngredientResponse toDTO(Ingredient ingredient) {
        Objects.requireNonNull(ingredient, "ingredient must not be null");
        Objects.requireNonNull(ingredient.getAliment(), "aliment must not be null");
        Objects.requireNonNull(ingredient.getUnit(), "unit must not be null");

        return new IngredientResponse(
                ingredient.getQuantityPerPerson(),
                alimentResponse.toDTO(ingredient.getAliment()),
                unitResponse.toDTO(ingredient.getUnit())
        );
    }
}
