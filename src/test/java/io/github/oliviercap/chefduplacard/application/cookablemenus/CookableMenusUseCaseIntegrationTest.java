package io.github.oliviercap.chefduplacard.application.cookablemenus;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
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
public class CookableMenusUseCaseIntegrationTest {

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

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa("apple", "fruit", true);
        UnitJpa gram = new UnitJpa("gramme", "g");

        // STOCK : 20g
        StockJpa stock = new StockJpa("test");
        stock.addStockLine(new StockLineJpa(apple, gram, BigDecimal.valueOf(20)));

        // RECIPES : 10g chacune
        RecipeJpa r1 = new RecipeJpa("r1", "desc1", 5, "1");
        RecipeJpa r2 = new RecipeJpa("r2", "desc2", 10, "1");

        r1.addIngredient(new IngredientJpa(r1, apple, gram, BigDecimal.valueOf(10)));
        r2.addIngredient(new IngredientJpa(r2, apple, gram, BigDecimal.valueOf(10)));

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(gram);
        stockJpaRepository.save(stock);
        recipeJpaRepository.save(r1);
        recipeJpaRepository.save(r2);

        // ===== WHEN =====

        CookableMenusRequestModel request = new CookableMenusRequestModel(
                "test",           // stockName
                2,                // nbMealToPrepare
                1,                // nbPeople
                List.of()         // no filters
        );

        useCase.execute(request);

        CookableMenusViewModel result = presenter.getViewModel();

        // ===== THEN =====

        assertThat(result.recipes())
                .hasSize(2);

        assertThat(result.recipes())
                .extracting(CookableMenusViewModel.RecipeViewModel::recipeName)
                .containsExactly("r1", "r2");
    }
}