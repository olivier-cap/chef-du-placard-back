package io.github.oliviercap.chefduplacard.application.getmenu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.IMenuJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.getmenu.presenters.GetMenuPresenter;
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
class GetMenuUseCaseIntegrationTest {

    @Autowired
    private GetMenuUseCase useCase;

    @Autowired
    private GetMenuPresenter presenter;

    @Autowired
    private IMenuJpaRepository menuJpaRepository;

    @Autowired
    private IRecipeJpaRepository recipeJpaRepository;

    @Autowired
    private IAlimentJpaRepository alimentJpaRepository;

    @Autowired
    private IUnitJpaRepository unitJpaRepository;

    @Test
    void should_get_menu_with_real_persistence_pipeline() {

        // ===== GIVEN =====

        AlimentJpa apple = new AlimentJpa(
                "integration-menu-apple",
                "fruit",
                true
        );

        AlimentJpa banana = new AlimentJpa(
                "integration-menu-banana",
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
                "integration-menu-apple-pie",
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
                "integration-menu-banana-cake",
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

        MenuJpa menu = new MenuJpa();
        menu.setName("integration-menu-weekend");

        menu.addMenuLine(
                new MenuLineJpa(
                        applePie,
                        BigDecimal.valueOf(2)
                )
        );

        menu.addMenuLine(
                new MenuLineJpa(
                        bananaCake,
                        BigDecimal.valueOf(4)
                )
        );

        menuJpaRepository.save(menu);

        // ===== WHEN =====

        GetMenuRequestModel request =
                new GetMenuRequestModel("integration-menu-weekend");

        useCase.execute(request);

        GetMenuViewModel result = presenter.getViewModel();

        // ===== THEN : menu =====

        assertThat(result)
                .isNotNull();

        assertThat(result.menuName())
                .isEqualTo("integration-menu-weekend");

        assertThat(result.menuLineViewModels())
                .hasSize(2);

        // ===== THEN : ligne apple pie =====

        assertThat(result.menuLineViewModels())
                .anySatisfy(menuLine -> {
                    assertThat(menuLine.nbPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(2));

                    assertThat(menuLine.recipeViewModel().name())
                            .isEqualTo("integration-menu-apple-pie");

                    assertThat(menuLine.recipeViewModel().instructions())
                            .isEqualTo("Cut apples and bake.");

                    assertThat(menuLine.recipeViewModel().duration())
                            .isEqualTo(Duration.ofMinutes(30));

                    assertThat(menuLine.recipeViewModel().difficulty())
                            .isEqualTo("easy");
                });

        // ===== THEN : ligne banana cake =====

        assertThat(result.menuLineViewModels())
                .anySatisfy(menuLine -> {
                    assertThat(menuLine.nbPerson())
                            .isEqualByComparingTo(BigDecimal.valueOf(4));

                    assertThat(menuLine.recipeViewModel().name())
                            .isEqualTo("integration-menu-banana-cake");

                    assertThat(menuLine.recipeViewModel().instructions())
                            .isEqualTo("Mix bananas and bake.");

                    assertThat(menuLine.recipeViewModel().duration())
                            .isEqualTo(Duration.ofMinutes(45));

                    assertThat(menuLine.recipeViewModel().difficulty())
                            .isEqualTo("medium");
                });
    }

    @Test
    void should_throw_domain_exception_when_menu_name_is_blank() {

        // ===== GIVEN =====

        GetMenuRequestModel request =
                new GetMenuRequestModel(" ");

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("menuName must not be blank");
    }

    @Test
    void should_throw_domain_exception_when_menu_does_not_exist() {

        // ===== GIVEN =====

        String unknownMenuName = "integration-unknown-menu";

        GetMenuRequestModel request =
                new GetMenuRequestModel(unknownMenuName);

        // ===== WHEN / THEN =====

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("menu not found " + unknownMenuName);
    }
}