package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
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

/**
 * Ce test ne vise pas a tester toute la logique metier.
 * Il valide que la chaine Spring, JPA, mappers et cas d'usage
 * est correctement cablee.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FindCookableRecipesUseCaseIntegrationTest {

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
        // Given: aliment and unit already present in database
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-cookable-recipes-apple",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-cookable-recipes",
                        "g-cookable-recipes"
                )
        );

        // Given: stock contains exactly the required quantity
        StockJpa stock = new StockJpa(
                "integration-cookable-recipes-stock"
        );

        stock.addStockLine(
                new StockLineJpa(
                        apple,
                        gram,
                        BigDecimal.valueOf(12)
                )
        );

        StockJpa savedStock = stockJpaRepository.save(stock);

        // Given: one recipe requires 12 units for one person
        RecipeJpa recipe = new RecipeJpa(
                "integration-cookable-recipes-r1",
                "Preparation instructions.",
                5,
                "easy"
        );

        recipe.addIngredient(
                new IngredientJpa(
                        recipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(12)
                )
        );

        recipeJpaRepository.save(recipe);

        FindCookableRecipesRequestModel request =
                new FindCookableRecipesRequestModel(
                        1,
                        savedStock.getId()
                );

        // When
        useCase.execute(request);

        FindCookableRecipesViewModel result = presenter.getViewModel();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.recipes()).hasSize(1);

        assertThat(result.recipes())
                .extracting(
                        FindCookableRecipesViewModel.RecipeViewModel::recipeName
                )
                .containsExactly("integration-cookable-recipes-r1");

        assertThat(result.recipes().getFirst().duration())
                .isEqualTo(Duration.ofMinutes(5));
    }
}