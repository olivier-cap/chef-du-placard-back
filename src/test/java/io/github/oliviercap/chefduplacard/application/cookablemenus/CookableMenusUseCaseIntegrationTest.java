package io.github.oliviercap.chefduplacard.application.cookablemenus;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.menu.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.menu.cookablemenus.presenters.CookableMenusPresenter;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusRequestModel;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusUseCase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @Autowired private CookableMenusUseCase useCase;
    @Autowired private CookableMenusPresenter presenter;
    @Autowired private IRecipeJpaRepository recipeJpaRepository;
    @Autowired private IStockJpaRepository stockJpaRepository;
    @Autowired private IAlimentJpaRepository alimentJpaRepository;
    @Autowired private IUnitJpaRepository unitJpaRepository;
    @PersistenceContext private EntityManager entityManager;

    @Test
    void should_build_cookable_menu_with_real_persistence_pipeline() {
        AlimentJpa apple = alimentJpaRepository.save(new AlimentJpa(
                "integration-cookable-menu-apple", "fruit", true));
        UnitJpa gram = unitJpaRepository.save(new UnitJpa(
                "gramme-cookable-menu", "g-cookable-menu"));

        UserJpa owner = new UserJpa(
                "cookable-menu-user", "cookable-menu@example.com", false);
        entityManager.persist(owner);

        StockJpa stock = new StockJpa(
                "integration-cookable-menu-stock", owner);
        stock.addStockLine(new StockLineJpa(
                apple, gram, BigDecimal.valueOf(20)));
        StockJpa savedStock = stockJpaRepository.save(stock);

        RecipeTypeJpa recipeType = new RecipeTypeJpa(
                "integration-cookable-menu-type");
        entityManager.persist(recipeType);

        RecipeJpa firstRecipe = new RecipeJpa(
                "integration-cookable-menu-r1", "desc1", 5, "1");
        recipeType.addRecipe(firstRecipe);
        firstRecipe.addIngredient(new IngredientJpa(
                firstRecipe, apple, gram, BigDecimal.valueOf(10)));

        RecipeJpa secondRecipe = new RecipeJpa(
                "integration-cookable-menu-r2", "desc2", 10, "1");
        recipeType.addRecipe(secondRecipe);
        secondRecipe.addIngredient(new IngredientJpa(
                secondRecipe, apple, gram, BigDecimal.valueOf(10)));

        recipeJpaRepository.save(firstRecipe);
        recipeJpaRepository.save(secondRecipe);

        useCase.execute(new CookableMenusRequestModel(
                savedStock.getId(), 2, 1, List.of()));

        CookableMenusViewModel result = presenter.getViewModel();

        assertThat(result).isNotNull();
        assertThat(result.recipes()).hasSize(2);
        assertThat(result.recipes())
                .extracting(CookableMenusViewModel.RecipeViewModel::recipeName)
                .containsExactly(
                        "integration-cookable-menu-r1",
                        "integration-cookable-menu-r2");
    }
}