package io.github.oliviercap.chefduplacard.application.getrecipelist;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.GetRecipeListViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.GetRecipeListViewModel.RecipeList;
import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.presenters.GetRecipeListPresenter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GetRecipeListUseCaseIntegrationTest {

    @Autowired
    private GetRecipeListUseCase useCase;

    @Autowired
    private GetRecipeListPresenter presenter;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_get_recipe_list_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa(
                "integration-recipe-list-apple",
                "fruit",
                true
        );

        AlimentJpa banana = new AlimentJpa(
                "integration-recipe-list-banana",
                "fruit",
                true
        );

        UnitJpa gram = new UnitJpa("gramme", "g");

        alimentJpaRepository.save(apple);
        alimentJpaRepository.save(banana);
        unitJpaRepository.save(gram);

        RecipeJpa recipe1 = new RecipeJpa(
                "integration-recipe-list-apple-pie",
                "apple pie instructions",
                30,
                "easy"
        );

        RecipeJpa recipe2 = new RecipeJpa(
                "integration-recipe-list-banana-cake",
                "banana cake instructions",
                45,
                "medium"
        );

        recipe1.addIngredient(
                new IngredientJpa(
                        recipe1,
                        apple,
                        gram,
                        BigDecimal.valueOf(100)
                )
        );

        recipe2.addIngredient(
                new IngredientJpa(
                        recipe2,
                        banana,
                        gram,
                        BigDecimal.valueOf(150)
                )
        );

        recipeJpaRepository.save(recipe1);
        recipeJpaRepository.save(recipe2);

        // ===== WHEN =====

        GetRecipeListRequestModel request = new GetRecipeListRequestModel();

        useCase.execute(request);

        GetRecipeListViewModel result = presenter.getViewModel();

        // ===== THEN =====

        assertThat(result)
                .isNotNull();

        assertThat(result.recipes())
                .isNotNull();

        assertThat(result.recipes())
                .extracting(
                        RecipeList::name,
                        RecipeList::duration,
                        RecipeList::difficulty
                )
                .contains(
                        tuple(
                                "integration-recipe-list-apple-pie",
                                Duration.ofMinutes(30),
                                "easy"
                        ),
                        tuple(
                                "integration-recipe-list-banana-cake",
                                Duration.ofMinutes(45),
                                "medium"
                        )
                );
    }
}