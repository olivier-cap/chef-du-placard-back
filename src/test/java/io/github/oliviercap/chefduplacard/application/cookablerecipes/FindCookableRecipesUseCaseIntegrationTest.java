package io.github.oliviercap.chefduplacard.application.cookablerecipes;


import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient.IIngredientJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stockline.IStockLineJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Ce test ne vise pas à tester toute la logique métier.
// Il valide que la chaîne Spring + JPA + mappers + use case est correctement câblée.

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindCookableRecipesUseCaseIntegrationTest {
    @Autowired
    private FindCookableRecipesUseCase useCase;

    // Repository JPA pour préparer les données
    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;
    @Autowired
    private IStockJpaRepository stockJpaRepository;
    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;
    @Autowired
    IUnitJpaRepository unitJpaRepository;
    @Autowired
    IStockLineJpaRepository stockLineJpaRepository;
    @Autowired
    IIngredientJpaRepository ingredientJpaRepository;

    @Test
    void should_find_cookable_recipes_with_real_persistence_pipeline() {
        // given : préparer la base H2
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa unit = new UnitJpa("gramme","g");
        StockJpa stock = new StockJpa("test", List.of());
        StockLineJpa stockLine = new StockLineJpa(stock, apple, unit, BigDecimal.valueOf(12));
        RecipeJpa recipeJpa = new RecipeJpa("r1","", 5,"1");
        IngredientJpa ingredient = new IngredientJpa(recipeJpa, apple, unit, BigDecimal.valueOf(12));

        stock.setStockLineJpa(List.of(stockLine));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(unit);
        stockJpaRepository.save(stock);
        recipeJpaRepository.save(recipeJpa);
        stockLineJpaRepository.save(stockLine);
        ingredientJpaRepository.save(ingredient);


        // when
        List<Recipe> result = useCase.execute(1);

        // then
        assertThat(result)
                .extracting(Recipe::getName)
                .containsExactly("r1");
    }


}
