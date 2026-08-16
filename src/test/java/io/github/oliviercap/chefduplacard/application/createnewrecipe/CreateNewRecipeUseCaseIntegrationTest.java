package io.github.oliviercap.chefduplacard.application.createnewrecipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.createnewrecipe.CreateNewRecipeViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.createnewrecipe.presenters.CreateNewRecipePresenter;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreateNewRecipeUseCaseIntegrationTest {

    @Autowired
    private CreateNewRecipeUseCase useCase;

    @Autowired
    private CreateNewRecipePresenter presenter;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_create_new_recipe_with_real_persistence_pipeline() {
        // Given
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-create-recipe-apple",
                        "fruit",
                        true
                )
        );

        AlimentJpa flour = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-create-recipe-flour",
                        "cereal",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-create-recipe",
                        "g-create-recipe"
                )
        );

        String recipeName = "integration-create-recipe-apple-pie";

        CreateNewRecipeRequestModel request =
                new CreateNewRecipeRequestModel(
                        recipeName,
                        "Cut the apples, add flour and bake.",
                        Duration.ofMinutes(45),
                        "easy",
                        List.of(
                                new IngredientsData(
                                        apple.getId(),
                                        gram.getId(),
                                        BigDecimal.valueOf(200)
                                ),
                                new IngredientsData(
                                        flour.getId(),
                                        gram.getId(),
                                        BigDecimal.valueOf(150)
                                )
                        )
                );

        // When
        useCase.execute(request);

        CreateNewRecipeViewModel result = presenter.getViewModel();

        // Then: presenter
        assertThat(result).isNotNull();
        assertThat(result.saved()).isTrue();

        // Then: real persistence
        RecipeJpa savedRecipe = findRecipeByName(recipeName);

        assertThat(savedRecipe.getName()).isEqualTo(recipeName);
        assertThat(savedRecipe.getInstructions())
                .isEqualTo("Cut the apples, add flour and bake.");
        assertThat(savedRecipe.getDurationMinutes()).isEqualTo(45);
        assertThat(savedRecipe.getDifficulty()).isEqualTo("easy");
        assertThat(savedRecipe.getIngredients()).hasSize(2);

        assertThat(savedRecipe.getIngredients())
                .anySatisfy(ingredient -> {
                    assertThat(ingredient.getAlimentJpa().getId())
                            .isEqualTo(apple.getId());
                    assertThat(ingredient.getAlimentJpa().getName())
                            .isEqualTo("integration-create-recipe-apple");
                    assertThat(ingredient.getUnitJpa().getId())
                            .isEqualTo(gram.getId());
                    assertThat(ingredient.getQuantityPerPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(200));
                });

        assertThat(savedRecipe.getIngredients())
                .anySatisfy(ingredient -> {
                    assertThat(ingredient.getAlimentJpa().getId())
                            .isEqualTo(flour.getId());
                    assertThat(ingredient.getAlimentJpa().getName())
                            .isEqualTo("integration-create-recipe-flour");
                    assertThat(ingredient.getUnitJpa().getId())
                            .isEqualTo(gram.getId());
                    assertThat(ingredient.getQuantityPerPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(150));
                });
    }

    @Test
    void should_create_new_recipe_without_ingredients() {
        // Given
        String recipeName =
                "integration-create-recipe-without-ingredients";

        CreateNewRecipeRequestModel request =
                new CreateNewRecipeRequestModel(
                        recipeName,
                        "No preparation required.",
                        Duration.ofMinutes(5),
                        "easy",
                        List.of()
                );

        // When
        useCase.execute(request);

        CreateNewRecipeViewModel result = presenter.getViewModel();

        // Then: presenter
        assertThat(result).isNotNull();
        assertThat(result.saved()).isTrue();

        // Then: real persistence
        RecipeJpa savedRecipe = findRecipeByName(recipeName);

        assertThat(savedRecipe.getName()).isEqualTo(recipeName);
        assertThat(savedRecipe.getInstructions())
                .isEqualTo("No preparation required.");
        assertThat(savedRecipe.getDurationMinutes()).isEqualTo(5);
        assertThat(savedRecipe.getDifficulty()).isEqualTo("easy");
        assertThat(savedRecipe.getIngredients()).isEmpty();
    }

    @Test
    void should_throw_domain_exception_when_recipe_name_already_exists() {
        // Given
        String recipeName = "integration-create-recipe-existing";

        recipeJpaRepository.save(
                new RecipeJpa(
                        recipeName,
                        "Existing recipe instructions.",
                        30,
                        "easy"
                )
        );

        CreateNewRecipeRequestModel request =
                new CreateNewRecipeRequestModel(
                        recipeName,
                        "New instructions.",
                        Duration.ofMinutes(45),
                        "medium",
                        List.of()
                );

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("impossible to save recipe " + recipeName)
                .hasCauseInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void should_throw_domain_exception_when_duration_is_null() {
        // Given
        String recipeName =
                "integration-create-recipe-null-duration";

        CreateNewRecipeRequestModel request =
                new CreateNewRecipeRequestModel(
                        recipeName,
                        "Recipe with invalid duration.",
                        null,
                        "easy",
                        List.of()
                );

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("impossible to save recipe " + recipeName)
                .hasCauseInstanceOf(NullPointerException.class);

        assertThat(recipeJpaRepository.findAll())
                .noneSatisfy(recipe ->
                        assertThat(recipe.getName()).isEqualTo(recipeName)
                );
    }

    private RecipeJpa findRecipeByName(String recipeName) {
        return recipeJpaRepository.findAllComplete().stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .findFirst()
                .orElseThrow();
    }
}