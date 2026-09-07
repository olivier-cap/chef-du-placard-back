package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe_type;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeTypeJpa;
import io.github.oliviercap.chefduplacard.domain.recipe_type.RecipeType;
import io.github.oliviercap.chefduplacard.domain.recipe_type.RecipeTypeId;
import org.springframework.stereotype.Component;

@Component
public class RecipeTypeMapper {

    public RecipeType toDomain(RecipeTypeJpa recipeTypeJpa) {
        return new RecipeType(
                new RecipeTypeId(recipeTypeJpa.getId()),
                recipeTypeJpa.getName()
        );
    }
}
