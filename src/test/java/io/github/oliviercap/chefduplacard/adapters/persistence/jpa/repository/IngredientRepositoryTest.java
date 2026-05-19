package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient.IIngredientJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class IngredientRepositoryTest {

    @Autowired
    private IIngredientJpaRepository ingredientJpaRepository;
    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;
    @Autowired
    private IUnitJpaRepository unitJpaRepository;
    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Test
    void save_and_load_ingredient() {
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa unit = new UnitJpa("gramme","g");
        RecipeJpa recipeJpa = new RecipeJpa("recipe","", 5,"1");

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(unit);
        recipeJpaRepository.save(recipeJpa);

        IngredientJpa ingredient = new IngredientJpa(recipeJpa, apple, unit, BigDecimal.valueOf(12));

        ingredientJpaRepository.save(ingredient);
        var result = ingredientJpaRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAlimentJpa().getName()).isEqualTo("apple");
        assertThat(result.getFirst()).isEqualTo(ingredient);
    }
}
