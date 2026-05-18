package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepository implements IRecipeRepository {
    private final IRecipeJpaRepository recipeJpaRepository;
    private final RecipeMapper recipeMapper;

    public RecipeRepository(
            IRecipeJpaRepository recipeJpaRepository,
            RecipeMapper recipeMapper
    ) {
        this.recipeJpaRepository = recipeJpaRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeJpaRepository.findAllComplete().stream()
                .map(recipeMapper::toDomain)
                .toList();
    }
}
