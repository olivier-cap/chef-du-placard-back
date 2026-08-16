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
import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.controllers.SaveNewMenuRequest;
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
        // Given: aliments and unit already present in database
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-save-menu-apple",
                        "fruit",
                        true
                )
        );

        AlimentJpa banana = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-save-menu-banana",
                        "fruit",
                        true
                )
        );

        UnitJpa gram = unitJpaRepository.save(
                new UnitJpa(
                        "gramme-save-menu",
                        "g-save-menu"
                )
        );

        // Given: recipes already present in database
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

        RecipeJpa savedApplePie =
                recipeJpaRepository.save(applePie);
        RecipeJpa savedBananaCake =
                recipeJpaRepository.save(bananaCake);

        String menuName = "integration-saved-menu-weekend";

        SaveNewMenuRequest requestContent =
                new SaveNewMenuRequest(
                        menuName,
                        List.of(
                                new SaveNewMenuRequest.MenuLine(
                                        BigDecimal.valueOf(2),
                                        savedApplePie.getId()
                                ),
                                new SaveNewMenuRequest.MenuLine(
                                        BigDecimal.valueOf(4),
                                        savedBananaCake.getId()
                                )
                        )
                );

        SaveNewMenuRequestModel request =
                new SaveNewMenuRequestModel(requestContent);

        // When
        useCase.execute(request);

        SaveNewMenuViewModel result = presenter.getViewModel();

        // Then: presenter
        assertThat(result).isNotNull();
        assertThat(result.saved()).isTrue();

        // Then: real persistence
        MenuJpa createdMenu = menuJpaRepository.findAll().stream()
                .filter(menu -> menu.getName().equals(menuName))
                .findFirst()
                .orElseThrow();

        MenuJpa savedMenu = menuJpaRepository
                .findMenuDetailsById(createdMenu.getId())
                .orElseThrow();

        assertThat(savedMenu.getName()).isEqualTo(menuName);
        assertThat(savedMenu.getMenuLineJpaList()).hasSize(2);

        assertThat(savedMenu.getMenuLineJpaList())
                .anySatisfy(menuLine -> {
                    assertThat(menuLine.getNbPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(2));
                    assertThat(menuLine.getRecipeJpa().getId())
                            .isEqualTo(savedApplePie.getId());
                    assertThat(menuLine.getRecipeJpa().getName())
                            .isEqualTo("integration-save-menu-apple-pie");
                });

        assertThat(savedMenu.getMenuLineJpaList())
                .anySatisfy(menuLine -> {
                    assertThat(menuLine.getNbPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(4));
                    assertThat(menuLine.getRecipeJpa().getId())
                            .isEqualTo(savedBananaCake.getId());
                    assertThat(menuLine.getRecipeJpa().getName())
                            .isEqualTo("integration-save-menu-banana-cake");
                });
    }

    @Test
    void should_throw_domain_exception_when_menu_name_is_blank() {
        // Given
        SaveNewMenuRequest requestContent =
                new SaveNewMenuRequest(
                        " ",
                        List.of()
                );

        SaveNewMenuRequestModel request =
                new SaveNewMenuRequestModel(requestContent);

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("menu name must not be blank");

        assertThat(menuJpaRepository.findAll()).isEmpty();
    }

    @Test
    void should_throw_domain_exception_when_recipe_does_not_exist() {
        // Given
        Long unknownRecipeId = 999999L;

        SaveNewMenuRequest requestContent =
                new SaveNewMenuRequest(
                        "integration-menu-with-unknown-recipe",
                        List.of(
                                new SaveNewMenuRequest.MenuLine(
                                        BigDecimal.valueOf(2),
                                        unknownRecipeId
                                )
                        )
                );

        SaveNewMenuRequestModel request =
                new SaveNewMenuRequestModel(requestContent);

        // When and then
        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("save of menu didn't work")
                .hasCauseInstanceOf(DomainException.class)
                .cause()
                .hasMessage(
                        "Recipe " + unknownRecipeId + " not found in base"
                );

        assertThat(menuJpaRepository.findAll()).isEmpty();
    }
}