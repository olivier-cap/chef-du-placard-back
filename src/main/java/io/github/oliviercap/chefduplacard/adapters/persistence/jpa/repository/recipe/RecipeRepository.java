package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.IngredientsData;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;

@Repository
public class RecipeRepository implements IRecipeRepository {
    private final IRecipeJpaRepository recipeJpaRepository;
    private final RecipeMapper recipeMapper;
    private final IAlimentRepository alimentRepository;
    private final IUnitRepository unitRepository;

    public RecipeRepository(
            IRecipeJpaRepository recipeJpaRepository,
            RecipeMapper recipeMapper,
            IAlimentRepository alimentRepository,
            IUnitRepository unitRepository
    ) {
        this.recipeJpaRepository = recipeJpaRepository;
        this.recipeMapper = recipeMapper;
        this.alimentRepository = alimentRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeJpaRepository.findAllComplete().stream()
                .map(recipeMapper::toDomain)
                .toList();
    }

    /**
     * Find a recipe in database by its name
     * @param recipeName
     * @return
     */
    @Override
    public Optional<Recipe> findByName(String recipeName) {
        return recipeJpaRepository.findCompleteByName(recipeName)
                .map(recipeMapper::toDomain);
    }

    @Override
    public Optional<RecipeJpa> findJpaById(Long id) {
        return recipeJpaRepository.findCompleteById(id);
    }

    @Override
    public Optional<RecipeJpa> findJpaByName(String recipeName) {
        return recipeJpaRepository.findCompleteByName(recipeName);
    }

    @Override
    public RecipeJpa getReferenceJpaById(Long id) {
        return recipeJpaRepository.getReferenceById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return recipeJpaRepository.existsByName(name);
    }

    //sauvegarde d'une nouvelle recette dans la base
    //réalisé à partir des données correspondant à la recette + liste d'ingrédients (aliment+quantité+unité)
    //Attention : les aliments et les unités doivent déjà être présents en base
    @Transactional
    @Override
    public void saveNew(
            String nameRecipe,
            String instructionsRecipe,
            Duration durationRecipe,
            String difficultyRecipe,
            List<IngredientsData> ingredients)
    {
        //On ne reçoit pas un recipe !
        //Objects.requireNonNull(recipe, "recipe must not be null");

        //Verification qu'une recette du même nom n'existe pas déjà
        //Impossible de vérifier par Id : par définition, une nouvelle recette n'a pas d'ID
        //NE PAS UTILISER un findByName : cela reviendrait à construire tout le Recipe, alors qu'il n'existe peut-être pas
        if(existsByName(nameRecipe)) {
            throw new DomainException("Recipe with name " + nameRecipe + "already exists");
        }

        /* ---- IMPORTANT ---- */
        //Récupération des objets Aliment et Unit exitants pour les rendre Managed par JPA.
        //Permet de ne pas recréer les aliments & unités déjà existants.

        //Recherche des aliments de la recette.
        //Les aliments DOIVENT déjà exister dans la base.
        //Ne pas aller les chercher par Name, préférer par id...
        Map<Long, AlimentJpa> alimentJpaList = new HashMap<>();
        for(IngredientsData ingredientsData : ingredients) {
            AlimentJpa alimentJpa = alimentRepository.findAlimentJpaById(ingredientsData.alimentId()).orElseThrow(
                    () -> new DomainException("aliment not found, id: " + ingredientsData.alimentId())
            );
            alimentJpaList.put(alimentJpa.getId(), alimentJpa);
        }

        //Recherche des unités de la recette.
        //Les unités DOIVENT déjà exister dans la base.
        //Préférer récupération des units par id...
        Map<Long,UnitJpa> unitJpaList = new HashMap<>();
        for(IngredientsData ingredientsData :ingredients) {
            UnitJpa unitJpa = unitRepository.findUnitJpaById(ingredientsData.unitID()).orElseThrow(
                    () -> new DomainException("unit not found, id:" + ingredientsData.unitID())
            );
            unitJpaList.put(unitJpa.getId(), unitJpa);
        }

        //Création de la nouvelle recetteJpa, sans ses ingrédients
        RecipeJpa recipeJpa = new RecipeJpa(
                nameRecipe,
                instructionsRecipe,
                (int) durationRecipe.toMinutes(), // rappel : pour l'instant stockage de la durée en int-minutes. à changer...
                difficultyRecipe
        );

        //Creation des nouveaux "ingrédients jpa" de la nouvelle recette
        //Ajout de ces ingrédients à la recette jpa
        //Sauvegarde automatique ? non -> recipeJpa n'est pas encore managed
        for(IngredientsData ingredientsData : ingredients) {
            recipeJpa.addIngredient(
                    new IngredientJpa(
                            recipeJpa,
                            alimentJpaList.get(ingredientsData.alimentId()),
                            unitJpaList.get(ingredientsData.unitID()),
                            ingredientsData.quantity()
                    )
            );
        }

        //Sauvegarde de recipejpa en base - aliments et units sont managed, pas de doublon
        recipeJpaRepository.save(recipeJpa);
    }


}
