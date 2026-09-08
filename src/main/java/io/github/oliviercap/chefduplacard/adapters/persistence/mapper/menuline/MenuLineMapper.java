package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menuline;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe_type.RecipeTypeMapper;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLineId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MenuLineMapper {

    private final RecipeMapper recipeMapper;
    private final RecipeTypeMapper recipeTypeMapper;

    public MenuLineMapper(RecipeMapper recipeMapper, RecipeTypeMapper recipeTypeMapper) {
        this.recipeMapper = recipeMapper;
        this.recipeTypeMapper = recipeTypeMapper;
    }

    public MenuLine toDomain(MenuLineJpa menuLineJpa) {
        Objects.requireNonNull(menuLineJpa, "menuLineJpa must not be null");
        Objects.requireNonNull(menuLineJpa.getRecipeJpa(), "recipeJpa must not be null");

        return new MenuLine(
                new MenuLineId(menuLineJpa.getId()),
                recipeMapper.toDomain(menuLineJpa.getRecipeJpa()),
                menuLineJpa.getNbPerson(),
                menuLineJpa.getDate(),
                recipeTypeMapper.toDomain(menuLineJpa.getRecipeTypeJpa())
        );
    }

}
