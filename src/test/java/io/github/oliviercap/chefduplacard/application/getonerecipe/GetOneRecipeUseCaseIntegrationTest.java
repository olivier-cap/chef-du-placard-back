package io.github.oliviercap.chefduplacard.application.getonerecipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.GetOneRecipeViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.presenters.GetOneRecipePresenter;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
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

    @Test
    void should_get_one_recipe_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa(
                "integration-one-recipe-apple",
                "fruit",
                true
        );

        UnitJpa gram = new UnitJpa(
                "gramme",
                "g"
        );

        alimentJpaRepository.save(apple);
        unitJpaRepository.save(gram);

        RecipeJpa recipe = new RecipeJpa(
                "integration-one-recipe-apple-pie",
                "Cut apples and bake.",
                30,
                "easy"
        );

        recipe.addIngredient(
                new IngredientJpa(
                        recipe,
                        apple,
                        gram,
                        BigDecimal.valueOf(100)
                )
        );

        recipeJpaRepository.save(recipe);

        // ===== WHEN =====

        GetOneRecipeRequestModel request =
                new GetOneRecipeRequestModel("integration-one-recipe-apple-pie");

        useCase.execute(request);

        GetOneRecipeViewModel result = presenter.getViewModel();

        // ===== THEN : recette =====

        assertThat(result)
                .isNotNull();

        assertThat(result.name())
                .isEqualTo("integration-one-recipe-apple-pie");

        assertThat(result.instructions())
                .isEqualTo("Cut apples and bake.");

        assertThat(result.duration())
                .isEqualTo(Duration.ofMinutes(30));

        assertThat(result.difficulty())
                .isEqualTo("easy");

        // ===== THEN : ingrédients =====

        assertThat(result.ingredients())
                .hasSize(1);

        GetOneRecipeViewModel.IngredientViewModel ingredient =
                result.ingredients().get(0);

        assertThat(ingredient.quantity())
                .isEqualByComparingTo(BigDecimal.valueOf(100));

        assertThat(ingredient.aliment().name())
                .isEqualTo("integration-one-recipe-apple");

        assertThat(ingredient.aliment().description())
                .isEqualTo("fruit");

        assertThat(ingredient.aliment().isActive())
                .isTrue();

        assertThat(ingredient.unit().name())
                .isEqualTo("gramme");

        assertThat(ingredient.unit().symbol())
                .isEqualTo("g");
    }

    @Test
    void should_throw_domain_exception_when_recipe_name_is_blank() {

        // ===== GIVEN =====

        GetOneRecipeRequestModel request =
                new GetOneRecipeRequestModel(" ");

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("recipeName must not be blank");
    }

    @Test
    void should_throw_domain_exception_when_recipe_does_not_exist() {

        // ===== GIVEN =====

        String unknownRecipeName = "integration-unknown-recipe";

        GetOneRecipeRequestModel request =
                new GetOneRecipeRequestModel(unknownRecipeName);

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("recipe not found " + unknownRecipeName);
    }
}