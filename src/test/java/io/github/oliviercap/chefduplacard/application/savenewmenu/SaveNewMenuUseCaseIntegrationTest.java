package io.github.oliviercap.chefduplacard.application.savenewmenu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.IMenuJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.UpdateStockRequest;
import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.presenters.SaveNewMenuPresenter;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SaveNewMenuUseCaseIntegrationTest {

    @Autowired
    private SaveNewMenuUseCase useCase;

    @Autowired
    private SaveNewMenuPresenter presenter;

    @Autowired
    private IMenuJpaRepository menuJpaRepository;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_save_new_menu_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa(
                "integration-save-menu-apple",
                "fruit",
                true
        );

        AlimentJpa banana = new AlimentJpa(
                "integration-save-menu-banana",
                "fruit",
                true
        );

        UnitJpa gram = new UnitJpa(
                "gramme",
                "g"
        );

        alimentJpaRepository.save(apple);
        alimentJpaRepository.save(banana);
        unitJpaRepository.save(gram);

        RecipeJpa applePie = new RecipeJpa(
                "integration-save-menu-apple-pie",
                "Cut apples and bake.",
                30,
                "easy"
        );

        applePie.addIngredient(
                new IngredientJpa(
                        applePie,
                        apple,
                        gram,
                        BigDecimal.valueOf(100)
                )
        );

        RecipeJpa bananaCake = new RecipeJpa(
                "integration-save-menu-banana-cake",
                "Mix bananas and bake.",
                45,
                "medium"
        );

        bananaCake.addIngredient(
                new IngredientJpa(
                        bananaCake,
                        banana,
                        gram,
                        BigDecimal.valueOf(150)
                )
        );

        recipeJpaRepository.save(applePie);
        recipeJpaRepository.save(bananaCake);

        UpdateStockRequest requestContent =
                new UpdateStockRequest(
                        "integration-saved-menu-weekend",
                        List.of(
                                new UpdateStockRequest.MenuLine(
                                        BigDecimal.valueOf(2),
                                        "integration-save-menu-apple-pie"
                                ),
                                new UpdateStockRequest.MenuLine(
                                        BigDecimal.valueOf(4),
                                        "integration-save-menu-banana-cake"
                                )
                        )
                );

        SaveNewMenuRequestModel request =
                new SaveNewMenuRequestModel(requestContent);

        // ===== WHEN =====

        useCase.execute(request);

        SaveNewMenuViewModel result = presenter.getViewModel();

        // ===== THEN : presenter =====

        assertThat(result)
                .isNotNull();

        assertThat(result.saved())
                .isTrue();

        // ===== THEN : persistence réelle =====

        MenuJpa savedMenu = menuJpaRepository
                .findMenuDetailsByName("integration-saved-menu-weekend")
                .orElseThrow();

        assertThat(savedMenu.getName())
                .isEqualTo("integration-saved-menu-weekend");

        assertThat(savedMenu.getMenuLineJpaList())
                .hasSize(2);

        assertThat(savedMenu.getMenuLineJpaList())
                .anySatisfy(menuLine -> {
                    assertThat(menuLine.getNbPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(2));

                    assertThat(menuLine.getRecipeJpa().getName())
                            .isEqualTo("integration-save-menu-apple-pie");
                });

        assertThat(savedMenu.getMenuLineJpaList())
                .anySatisfy(menuLine -> {
                    assertThat(menuLine.getNbPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(4));

                    assertThat(menuLine.getRecipeJpa().getName())
                            .isEqualTo("integration-save-menu-banana-cake");
                });
    }

    @Test
    void should_throw_domain_exception_when_menu_name_is_blank() {

        // ===== GIVEN =====

        UpdateStockRequest requestContent =
                new UpdateStockRequest(
                        " ",
                        List.of()
                );

        SaveNewMenuRequestModel request =
                new SaveNewMenuRequestModel(requestContent);

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("menu name must not be blank");
    }

    @Test
    void should_throw_domain_exception_when_recipe_does_not_exist() {

        // ===== GIVEN =====

        UpdateStockRequest requestContent =
                new UpdateStockRequest(
                        "integration-menu-with-unknown-recipe",
                        List.of(
                                new UpdateStockRequest.MenuLine(
                                        BigDecimal.valueOf(2),
                                        "integration-unknown-recipe"
                                )
                        )
                );

        SaveNewMenuRequestModel request =
                new SaveNewMenuRequestModel(requestContent);

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("Recipe integration-unknown-recipe not found");
    }
}