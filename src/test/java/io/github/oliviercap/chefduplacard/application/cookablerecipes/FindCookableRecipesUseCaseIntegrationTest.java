package io.github.oliviercap.chefduplacard.application.cookablerecipes;


import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.FindCookableRecipesPresenter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

// Ce test ne vise pas à tester toute la logique métier.
// Il valide que la chaîne Spring + JPA + mappers + use case est correctement câblée.

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindCookableRecipesUseCaseIntegrationTest {

    @Autowired
    private FindCookableRecipesUseCase useCase;

    @Autowired
    private FindCookableRecipesPresenter presenter;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;
    @Autowired
    private IStockJpaRepository stockJpaRepository;
    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;
    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_find_cookable_recipes_with_real_persistence_pipeline() {
        // GIVEN
        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa unit = new UnitJpa("gramme", "g");

        StockJpa stock = new StockJpa("test");
        StockLineJpa stockLine = new StockLineJpa(apple, unit, BigDecimal.valueOf(12));
        stock.addStockLine(stockLine);

        RecipeJpa recipeJpa = new RecipeJpa("r1", "a", 5, "1");

        IngredientJpa ingredient =
                new IngredientJpa(recipeJpa, apple, unit, BigDecimal.valueOf(12));

        recipeJpa.addIngredient(ingredient);

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(unit);
        stockJpaRepository.save(stock);
        recipeJpaRepository.save(recipeJpa);

        // WHEN
        useCase.execute(new FindCookableRecipesRequestModel(1, "test"));

        FindCookableRecipesViewModel result = presenter.getViewModel();

        // THEN
        assertThat(result.recipes())
                .extracting(FindCookableRecipesViewModel.RecipeViewModel::recipeName)
                .containsExactly("r1");

        assertThat(result.recipes().getFirst().duration())
                .isEqualTo(Duration.ofMinutes(5));
    }
}