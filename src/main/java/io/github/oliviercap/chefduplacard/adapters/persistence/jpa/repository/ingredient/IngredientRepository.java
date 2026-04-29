package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.ingredient.IIngredientJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IIngredientMapper;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class IngredientRepository implements IIngredientRepository{
    private final IIngredientJpaRepository ingredientJpaRepository;
    private final IIngredientMapper ingredientMapper;
    private final IAlimentJpaToDtoConverter alimentJpaToDtoConverter;
    private final IUnitJpaToDtoConverter unitJpaToDtoConverter;
    private final IIngredientJpaToDtoConverter ingredientJpaToDtoConverter;


    public IngredientRepository(IIngredientJpaRepository ingredientJpaRepository, IIngredientMapper ingredientMapper,
                                IAlimentJpaToDtoConverter alimentJpaToDtoConverter, IUnitJpaToDtoConverter unitJpaToDtoConverter,
                                IIngredientJpaToDtoConverter ingredientJpaToDtoConverter) {
        this.ingredientJpaRepository = ingredientJpaRepository;
        this.ingredientMapper = ingredientMapper;
        this.alimentJpaToDtoConverter = alimentJpaToDtoConverter;
        this.unitJpaToDtoConverter = unitJpaToDtoConverter;
        this.ingredientJpaToDtoConverter = ingredientJpaToDtoConverter;
    }

    @Override
    public Optional<Ingredient> findCompleteById(Long id) {
        return ingredientJpaRepository.findCompleteById(id)
                .map(ingredientJpaToDtoConverter::toDTO)
                .map(ingredientMapper::toDomain);
    }


}
