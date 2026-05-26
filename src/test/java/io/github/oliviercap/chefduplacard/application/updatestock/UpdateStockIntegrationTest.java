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

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa gram = new UnitJpa("gramme", "g");

        StockJpa stock = new StockJpa("test-stock");
        stock.addStockLine(new StockLineJpa(apple, gram, BigDecimal.valueOf(20)));

        RecipeJpa recipe = new RecipeJpa("apple-recipe", "desc", 5, "1");
        recipe.addIngredient(new IngredientJpa(recipe, apple, gram, BigDecimal.valueOf(10)));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(gram);
        stockJpaRepository.save(stock);
        recipeJpaRepository.save(recipe);

        // ===== WHEN =====

        UpdateStockRequestModel request = new UpdateStockRequestModel(
                "apple-recipe",
                1,
                "test-stock"
        );

        useCase.execute(request);

        UpdateStockViewModel result = presenter.getViewModel();

        // ===== THEN : réponse du use case =====

        assertThat(result.sufficientStock())
                .isTrue();

        assertThat(result.responseMessage())
                .isEqualTo("Stock Updated, sufficient initial stock");

        // ===== THEN : vérification de la persistance réelle =====

        StockJpa updatedStock = stockJpaRepository.findCompleteByName("test-stock")
                .orElseThrow();

        assertThat(updatedStock.getStockLineJpa())
                .hasSize(1);

        assertThat(updatedStock.getStockLineJpa().getFirst().getQuantity())
                .isEqualByComparingTo(BigDecimal.valueOf(10));
    }

    @Test
    void should_correct_stock_to_zero_when_initial_stock_is_insufficient() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa gram = new UnitJpa("gramme", "g");

        StockJpa stock = new StockJpa("test-stock");
        stock.addStockLine(new StockLineJpa(apple, gram, BigDecimal.valueOf(5)));

        RecipeJpa recipe = new RecipeJpa("apple-recipe", "desc", 5, "1");
        recipe.addIngredient(new IngredientJpa(recipe, apple, gram, BigDecimal.valueOf(10)));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(gram);
        stockJpaRepository.save(stock);
        recipeJpaRepository.save(recipe);

        // ===== WHEN =====

        UpdateStockRequestModel request = new UpdateStockRequestModel(
                "apple-recipe",
                1,
                "test-stock"
        );

        useCase.execute(request);

        UpdateStockViewModel result = presenter.getViewModel();

        // ===== THEN : réponse du use case =====

        assertThat(result.sufficientStock())
                .isFalse();

        assertThat(result.responseMessage())
                .isEqualTo("Stock Corrected, insufficient initial stock");

        // ===== THEN : vérification de la persistance réelle =====

        StockJpa updatedStock = stockJpaRepository.findCompleteByName("test-stock")
                .orElseThrow();

        assertThat(updatedStock.getStockLineJpa())
                .hasSize(1);

        assertThat(updatedStock.getStockLineJpa().getFirst().getQuantity())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }
}