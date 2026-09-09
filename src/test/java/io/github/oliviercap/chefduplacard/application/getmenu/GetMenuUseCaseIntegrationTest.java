package io.github.oliviercap.chefduplacard.application.getmenu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeTypeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UserJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.IAlimentJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.IMenuJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.IUnitJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.web.menu.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.menu.getmenu.presenters.GetMenuPresenter;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuUseCase;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void should_get_menu_with_real_persistence_pipeline() {
        AlimentJpa apple = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-menu-apple",
                        "fruit",
                        true
                )
        );

        AlimentJpa banana = alimentJpaRepository.save(
                new AlimentJpa(
                        "integration-menu-banana",
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
                "integration-menu-dessert"
        );
        entityManager.persist(recipeType);

        RecipeJpa applePie = new RecipeJpa(
                "integration-menu-apple-pie",
                "Cut apples and bake.",
                30,
                "easy"
        );
        recipeType.addRecipe(applePie);

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
        recipeType.addRecipe(bananaCake);

        bananaCake.addIngredient(
                new IngredientJpa(
                        bananaCake,
                        banana,
                        gram,
                        BigDecimal.valueOf(150)
                )
        );

        RecipeJpa savedApplePie = recipeJpaRepository.save(applePie);
        RecipeJpa savedBananaCake = recipeJpaRepository.save(bananaCake);

        UserJpa owner = new UserJpa(
                "get-menu-user",
                "get-menu@example.com",
                false
        );
        entityManager.persist(owner);

        MenuJpa menu = new MenuJpa();
        menu.setName("integration-menu-weekend");
        menu.setUserJpa(owner);

        menu.addMenuLine(
                new MenuLineJpa(
                        menu,
                        savedApplePie,
                        recipeType,
                        BigDecimal.valueOf(2),
                        null
                )
        );

        menu.addMenuLine(
                new MenuLineJpa(
                        menu,
                        savedBananaCake,
                        recipeType,
                        BigDecimal.valueOf(4),
                        null
                )
        );

        MenuJpa savedMenu = menuJpaRepository.save(menu);

        GetMenuRequestModel request =
                new GetMenuRequestModel(savedMenu.getId());

        useCase.execute(request);

        GetMenuViewModel result = presenter.getViewModel();

        assertThat(result).isNotNull();
        assertThat(result.menuName())
                .isEqualTo("integration-menu-weekend");
        assertThat(result.menuLineViewModels()).hasSize(2);

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
    void should_throw_domain_exception_when_menu_id_is_null() {
        GetMenuRequestModel request =
                new GetMenuRequestModel(null);

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("menuId must not be blank");
    }

    @Test
    void should_throw_domain_exception_when_menu_does_not_exist() {
        Long unknownMenuId = 999999L;

        GetMenuRequestModel request =
                new GetMenuRequestModel(unknownMenuId);

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .hasMessage("menu not found " + unknownMenuId);
    }
}