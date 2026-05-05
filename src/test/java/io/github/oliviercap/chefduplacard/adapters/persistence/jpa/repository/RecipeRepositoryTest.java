package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient.IIngredientJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RecipeRepositoryTest {

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;
    @Autowired
    IIngredientJpaRepository ingredientJpaRepository;
    @Autowired
    IAlimentJpaRepository alimentJpaRepository;
    @Autowired
    IUnitJpaRepository unitJpaRepository;

    @Test
    void save_and_load_recipe(){
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa unit = new UnitJpa("gramme","g");
        RecipeJpa recipeJpa = new RecipeJpa("name", "instructions", 1, "difficulty");
        IngredientJpa ingredientJpa = new IngredientJpa(recipeJpa, apple, unit, BigDecimal.valueOf(12));

        recipeJpa.setIngredients(List.of(ingredientJpa));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(unit);
        recipeJpaRepository.save(recipeJpa);

        ingredientJpaRepository.save(ingredientJpa);


        var result = recipeJpaRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIngredients().getFirst().getAlimentJpa().getName()).isEqualTo("apple");
        assertThat(result.getFirst()).isEqualTo(recipeJpa);

    }
}
