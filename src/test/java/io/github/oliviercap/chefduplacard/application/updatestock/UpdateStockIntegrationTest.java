package io.github.oliviercap.chefduplacard.application.updatestock;

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
import io.github.oliviercap.chefduplacard.adapters.web.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.updatestock.presenters.UpdateStockPresenter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UpdateStockIntegrationTest {

    @Autowired
    private UpdateStockUseCase useCase;

    @Autowired
    private UpdateStockPresenter presenter;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IStockJpaRepository stockJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_update_stock_when_initial_stock_is_sufficient() {
        // Given
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-update-stock-sufficient-apple",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-sufficient",
                        "g-sufficient"
                )
        );

        StockJpa stock = new StockJpa(
                "integration-update-stock-sufficient"
        );
        stock.addStockLine(
                new StockLineJpa(
                        apple,
                        gram,
                        BigDecimal.valueOf(20)
                )
        );

        StockJpa savedStock = stockJpaRepository.save(stock);

        RecipeJpa recipe = new RecipeJpa(
                "integration-update-stock-sufficient-recipe",
                "desc",
                5,
                "1"
        );
        recipe.addIngredient(
                new IngredientJpa(
                        recipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(10)
                )
        );

        RecipeJpa savedRecipe = recipeJpaRepository.save(recipe);

        UpdateStockRequestModel request = new UpdateStockRequestModel(
                savedStock.getId(),
                savedRecipe.getId(),
                1
        );

        // When
        useCase.execute(request);

        UpdateStockViewModel result = presenter.getViewModel();

        // Then: use-case response
        assertThat(result.sufficientStock()).isTrue();
        assertThat(result.responseMessage())
                .isEqualTo("Stock Updated, sufficient initial stock");

        // Then: real persistence
        StockJpa updatedStock = stockJpaRepository
                .findCompleteById(savedStock.getId())
                .orElseThrow();

        assertThat(updatedStock.getStockLineJpa()).hasSize(1);
        assertThat(updatedStock.getStockLineJpa().getFirst().getQuantity())
                .isEqualByComparingTo(BigDecimal.valueOf(10));
    }

    @Test
    void should_correct_stock_to_zero_when_initial_stock_is_insufficient() {
        // Given
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-update-stock-insufficient-apple",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-insufficient",
                        "g-insufficient"
                )
        );

        StockJpa stock = new StockJpa(
                "integration-update-stock-insufficient"
        );
        stock.addStockLine(
                new StockLineJpa(
                        apple,
                        gram,
                        BigDecimal.valueOf(5)
                )
        );

        StockJpa savedStock = stockJpaRepository.save(stock);

        RecipeJpa recipe = new RecipeJpa(
                "integration-update-stock-insufficient-recipe",
                "desc",
                5,
                "1"
        );
        recipe.addIngredient(
                new IngredientJpa(
                        recipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(10)
                )
        );

        RecipeJpa savedRecipe = recipeJpaRepository.save(recipe);

        UpdateStockRequestModel request = new UpdateStockRequestModel(
                savedStock.getId(),
                savedRecipe.getId(),
                1
        );

        // When
        useCase.execute(request);

        UpdateStockViewModel result = presenter.getViewModel();

        // Then: use-case response
        assertThat(result.sufficientStock()).isFalse();
        assertThat(result.responseMessage())
                .isEqualTo("Stock Corrected, insufficient initial stock");

        // Then: real persistence
        StockJpa updatedStock = stockJpaRepository
                .findCompleteById(savedStock.getId())
                .orElseThrow();

        assertThat(updatedStock.getStockLineJpa()).hasSize(1);
        assertThat(updatedStock.getStockLineJpa().getFirst().getQuantity())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }
}