package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.recipes.findcookablerecipes.FindCookableRecipesViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.recipes.findcookablerecipes.presenters.FindCookableRecipesPresenter;
import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesUseCase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FindCookableRecipesUseCaseIntegrationTest {

    @Autowired private FindCookableRecipesUseCase useCase;
    @Autowired private FindCookableRecipesPresenter presenter;
    @Autowired private IRecipeJpaRepository recipeJpaRepository;
    @Autowired private IStockJpaRepository stockJpaRepository;
    @Autowired private IAlimentJpaRepository alimentJpaRepository;
    @Autowired private IUnitJpaRepository unitJpaRepository;
    @PersistenceContext private EntityManager entityManager;

    @Test
    void should_find_cookable_recipes_with_real_persistence_pipeline() {
        AlimentJpa apple = alimentJpaRepository.save(new AlimentJpa(
                "integration-cookable-recipes-apple", "fruit", true));
        UnitJpa gram = unitJpaRepository.save(new UnitJpa(
                "gramme-cookable-recipes", "g-cookable-recipes"));

        UserJpa owner = new UserJpa(
                "cookable-recipes-user", "cookable-recipes@example.com", false);
        entityManager.persist(owner);

        StockJpa stock = new StockJpa(
                "integration-cookable-recipes-stock", owner);
        stock.addStockLine(new StockLineJpa(
                apple, gram, BigDecimal.valueOf(12)));
        StockJpa savedStock = stockJpaRepository.save(stock);

        RecipeTypeJpa recipeType = new RecipeTypeJpa(
                "integration-cookable-recipes-type");
        entityManager.persist(recipeType);

        RecipeJpa recipe = new RecipeJpa(
                "integration-cookable-recipes-r1",
                "Preparation instructions.", 5, "easy");
        recipeType.addRecipe(recipe);
        recipe.addIngredient(new IngredientJpa(
                recipe, apple, gram, BigDecimal.valueOf(12)));
        recipeJpaRepository.save(recipe);

        useCase.execute(new FindCookableRecipesRequestModel(
                1, savedStock.getId()));

        FindCookableRecipesViewModel result = presenter.getViewModel();

        assertThat(result).isNotNull();
        assertThat(result.recipes()).hasSize(1);
        assertThat(result.recipes())
                .extracting(FindCookableRecipesViewModel.RecipeViewModel::recipeName)
                .containsExactly("integration-cookable-recipes-r1");
        assertThat(result.recipes().getFirst().duration())
                .isEqualTo(Duration.ofMinutes(5));
    }
}