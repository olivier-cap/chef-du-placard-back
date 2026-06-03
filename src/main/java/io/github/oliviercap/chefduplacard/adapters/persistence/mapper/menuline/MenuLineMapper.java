package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menuline;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MenuLineMapper {

    private final RecipeMapper recipeMapper;

    public MenuLineMapper(RecipeMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }

    public MenuLine toDomain(MenuLineJpa menuLineJpa) {
        Objects.requireNonNull(menuLineJpa, "menuLineJpa must not be null");
        Objects.requireNonNull(menuLineJpa.getRecipeJpa(), "recipeJpa must not be null");

        return new MenuLine(
                recipeMapper.toDomain(menuLineJpa.getRecipeJpa()),
                menuLineJpa.getNbPerson()
        );
    }
    
}
