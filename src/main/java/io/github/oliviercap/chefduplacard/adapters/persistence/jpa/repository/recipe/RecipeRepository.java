package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.ingredient.IIngredientJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.RecipeDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.IRecipeMapper;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepository implements IRecipeRepository{
    private final IRecipeJpaRepository recipeJpaRepository;
    private final IRecipeMapper recipeMapper;
    private final IIngredientJpaToDtoConverter ingredientJpaToDtoConverter;

    public RecipeRepository(IRecipeJpaRepository recipeJpaRepository, IRecipeMapper recipeMapper,
                            IIngredientJpaToDtoConverter ingredientJpaToDtoConverter) {
        this.recipeJpaRepository = recipeJpaRepository;
        this.recipeMapper = recipeMapper;
        this.ingredientJpaToDtoConverter = ingredientJpaToDtoConverter;
    }


    @Override
    public List<Recipe> findAll() {
        return recipeJpaRepository.findAll().stream()
                .map(this::toDTO)
                .map(recipeMapper::toDomain)
                .toList();
    }

    private RecipeDTO toDTO(RecipeJpa recipeJpa) {
        return new RecipeDTO(
                recipeJpa.getName(),
                recipeJpa.getInstructions(),
                recipeJpa.getDurationMinutes(),
                recipeJpa.getDifficulty(),
                recipeJpa.getIngredients().stream()
                        .map(ingredientJpaToDtoConverter::toDTO)
                        .toList()
        );
    }

}
