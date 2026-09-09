package io.github.oliviercap.chefduplacard.application.getonerecipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeTypeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.recipes.getonerecipe.GetOneRecipeViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.recipes.getonerecipe.presenters.GetOneRecipePresenter;
import io.github.oliviercap.chefduplacard.application.recipes.getonerecipe.GetOneRecipeRequestModel;
import io.github.oliviercap.chefduplacard.application.recipes.getonerecipe.GetOneRecipeUseCase;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GetOneRecipeUseCaseIntegrationTest {

    @Autowired
    private GetOneRecipeUseCase useCase;

    @Autowired
    private GetOneRecipePresenter presenter;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void should_get_one_recipe_with_real_persistence_pipeline() {
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-one-recipe-apple",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme",
                        "g"
                )
        );

        RecipeTypeJpa recipeType = new RecipeTypeJpa(
                "integration-one-recipe-dessert"
        );
        entityManager.persist(recipeType);

        RecipeJpa recipe = new RecipeJpa(
                "integration-one-recipe-apple-pie",
                "Cut apples and bake.",
                30,
                "easy"
        );
        recipeType.addRecipe(recipe);

        recipe.addIngredient(
                new IngredientJpa(
                        recipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(100)
                )
        );

        RecipeJpa savedRecipe = recipeJpaRepository.save(recipe);

        GetOneRecipeRequestModel request =
                new GetOneRecipeRequestModel(savedRecipe.getId());

        useCase.execute(request);

        GetOneRecipeViewModel result = presenter.getViewModel();

        assertThat(result).isNotNull();
        assertThat(result.name())
                .isEqualTo("integration-one-recipe-apple-pie");
        assertThat(result.instructions())
                .isEqualTo("Cut apples and bake.");
        assertThat(result.duration())
                .isEqualTo(Duration.ofMinutes(30));
        assertThat(result.difficulty())
                .isEqualTo("easy");

        assertThat(result.ingredients()).hasSize(1);

        GetOneRecipeViewModel.IngredientViewModel ingredient =
                result.ingredients().getFirst();

        assertThat(ingredient.quantity())
                .isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(ingredient.aliment().name())
                .isEqualTo("integration-one-recipe-apple");
        assertThat(ingredient.aliment().description())
                .isEqualTo("fruit");
        assertThat(ingredient.aliment().isActive()).isTrue();
        assertThat(ingredient.unit().name()).isEqualTo("gramme");
        assertThat(ingredient.unit().symbol()).isEqualTo("g");
    }

    @Test
    void should_throw_domain_exception_when_recipe_id_is_null() {
        GetOneRecipeRequestModel request =
                new GetOneRecipeRequestModel(null);

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("recipeId must not be null");
    }

    @Test
    void should_throw_domain_exception_when_recipe_does_not_exist() {
        Long unknownRecipeId = 999999L;

        GetOneRecipeRequestModel request =
                new GetOneRecipeRequestModel(unknownRecipeId);

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("recipe not found " + unknownRecipeId);
    }
}