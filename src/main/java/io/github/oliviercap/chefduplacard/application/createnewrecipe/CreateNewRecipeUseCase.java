package io.github.oliviercap.chefduplacard.application.createnewrecipe;

import io.github.oliviercap.chefduplacard.application.createnewrecipe.ports.ICreateNewRecipeInputPort;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.ports.ICreateNewRecipeOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.time.Duration;
import java.util.List;

public class CreateNewRecipeUseCase implements ICreateNewRecipeInputPort {

    private final IRecipeRepository recipeRepository;
    public final ICreateNewRecipeOutputPort outputPort;

    public CreateNewRecipeUseCase(IRecipeRepository recipeRepository,
                                  ICreateNewRecipeOutputPort outputPort
    ) {
        this.recipeRepository = recipeRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(CreateNewRecipeRequestModel requestModel) {
            boolean saved = createNewRecipe(
                    requestModel.name(),
                    requestModel.instructions(),
                    requestModel.duration(),
                    requestModel.difficulty(),
                    requestModel.ingredients()
            ) ? true: false;

        outputPort.newRecipeSaved(new CreateNewRecipeResponseModel(saved));
    }

    /**
     * Création d'une nouvelle recette en base.
     * 1. Fabrication d'une recette "métier" depuis les données
     * 2. Vérification par recipe.check() que la recette est valide
     * 3. Enregistrement de la recette
     * @param name
     * @param instructions
     * @param duration
     * @param difficulty
     * @param ingredients
     */
    private boolean createNewRecipe(String name,
                                 String instructions,
                                 Duration duration,
                                 String difficulty,
                                 List<IngredientsData> ingredients) {

        //Ne fonctionne pas : on ne récupère pas depuis le front les objets complets, encore moins les objets métiers
        //on récupère probablement plus les infos recettes + les ids & aliments & units
        //pas possibilité de construire un objet domaine à ce stade
        //on récupère donc des infos du type {id_Aliment, id_Unité, quantité}
        //peut être transporté sous la forme de Records, en listes

        try{
            recipeRepository.saveNew(
                    name,
                    instructions,
                    duration,
                    difficulty,
                    ingredients);
        } catch(Exception e) {
            throw new DomainException("impossible to save recipe " + name, e);
        }

        return true;
    }
}
