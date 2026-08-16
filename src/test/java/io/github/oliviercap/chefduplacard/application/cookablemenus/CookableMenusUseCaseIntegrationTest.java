package io.github.oliviercap.chefduplacard.application.cookablemenus;

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
import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.presenters.CookableMenusPresenter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CookableMenusUseCaseIntegrationTest {

    @Autowired
    private CookableMenusUseCase useCase;

    @Autowired
    private CookableMenusPresenter presenter;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IStockJpaRepository stockJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_build_cookable_menu_with_real_persistence_pipeline() {
        // Given: aliment and unit already present in database
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-cookable-menu-apple",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-cookable-menu",
                        "g-cookable-menu"
                )
        );

        // Given: stock contains 20 units of apple
        StockJpa stock = new StockJpa(
                "integration-cookable-menu-stock"
        );

        stock.addStockLine(
                new StockLineJpa(
                        apple,
                        gram,
                        BigDecimal.valueOf(20)
                )
        );

        StockJpa savedStock = stockJpaRepository.save(stock);

        // Given: two recipes require 10 units of apple each
        RecipeJpa firstRecipe = new RecipeJpa(
                "integration-cookable-menu-r1",
                "desc1",
                5,
                "1"
        );

        firstRecipe.addIngredient(
                new IngredientJpa(
                        firstRecipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(10)
                )
        );

        RecipeJpa secondRecipe = new RecipeJpa(
                "integration-cookable-menu-r2",
                "desc2",
                10,
                "1"
        );

        secondRecipe.addIngredient(
                new IngredientJpa(
                        secondRecipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(10)
                )
        );

        recipeJpaRepository.save(firstRecipe);
        recipeJpaRepository.save(secondRecipe);

        CookableMenusRequestModel request =
                new CookableMenusRequestModel(
                        savedStock.getId(),
                        2,
                        1,
                        List.of()
                );

        // When
        useCase.execute(request);

        CookableMenusViewModel result = presenter.getViewModel();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.recipes()).hasSize(2);

        assertThat(result.recipes())
                .extracting(
                        CookableMenusViewModel.RecipeViewModel::recipeName
                )
                .containsExactly(
                        "integration-cookable-menu-r1",
                        "integration-cookable-menu-r2"
                );
    }
}