    package io.github.oliviercap.chefduplacard.application.cookablemenus;

    import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
    import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
    import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
    import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
    import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
    import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
    import io.github.oliviercap.chefduplacard.domain.recipefilters.RecipeFilter;
    import io.github.oliviercap.chefduplacard.domain.recipefilters.RecipeFilteringService;
    import io.github.oliviercap.chefduplacard.domain.reciperanking.RecipeRankingService;
    import io.github.oliviercap.chefduplacard.domain.stock.Stock;
    import io.github.oliviercap.chefduplacard.domain.stock.virtualstock.VirtualStock;
    import io.github.oliviercap.chefduplacard.domain.stock.virtualstock.VirtualStockFactory;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;

    /**
     * Recherche d'une liste de recettes à 100% réalisables.
     * Retourne un ensemble de listes de recettes. Filtration possible.
     */
    public class CookableMenusUseCase implements ICookableMenusInputPort {

        private final IRecipeRepository recipeRepository;
        private final IStockRepository stockRepository;
        private final ICookableMenusOutputPort cookableMenusOutputPort;
        private final RecipeFilteringService recipeFilteringService = new RecipeFilteringService();
        private final RecipeRankingService recipeRankingService = new RecipeRankingService();


        public CookableMenusUseCase(IRecipeRepository recipeRepository,
                                    IStockRepository stockRepository,
                                    ICookableMenusOutputPort cookableMenusOutputPort
        ) {
            this.recipeRepository = recipeRepository;
            this.stockRepository = stockRepository;
            this.cookableMenusOutputPort = cookableMenusOutputPort;
        }

        @Override
        public void execute(CookableMenusRequestModel cookableMenusRequestModel) {
            Objects.requireNonNull(cookableMenusRequestModel, "cookableMenusRequestModel must not be null");

            List<Recipe> menu = findCookableMenus(
                    cookableMenusRequestModel.stockName(),
                    cookableMenusRequestModel.nbMealToPrepare(),
                    cookableMenusRequestModel.nbPeople(),
                    cookableMenusRequestModel.recipeFilters()
            );

            String message;
            if (menu.size() == cookableMenusRequestModel.nbMealToPrepare()) {
                message = "nbmeal recipes founded";
            } else if (menu.size() > 0) {
                message = "insufficient stock or filters";
            } else {
                message = "no recipe found";
            }

            CookableMenusResponseModel responseModel = new CookableMenusResponseModel(
                    menu.size() == cookableMenusRequestModel.nbMealToPrepare(),
                    menu.stream()
                            .map(RecipeResponse::from)
                            .toList(),
                    message
            );

            cookableMenusOutputPort.displayCookableMenus(responseModel);
        }

        /**
         * Recherche des recettes réalisables pour nbPeople personnes, en fonction des filtres choisis.
         * Recherche au mieux nbMealToPrepare recettes
         * @param stockName
         * @param nbMealToPrepare
         * @param nbPeople
         * @return
         */
        private List<Recipe> findCookableMenus(String stockName, int nbMealToPrepare, int nbPeople, List<RecipeFilter> recipeFilters) {
            List<Recipe> menusRecipes = new ArrayList<>(); //liste des recettes du menu

            Stock stock = stockRepository.findByName(stockName).orElseThrow();
            VirtualStockFactory virtualStockFactory = new VirtualStockFactory();
            VirtualStock virtualStock = virtualStockFactory.createForMenuPreparation(stock);//Utilisation du virtualstock exclusivement ici

            List<Recipe> recipes = recipeRepository.findAll();
            boolean recipeCandidatesExist = !recipes.isEmpty();

            while (recipeCandidatesExist && menusRecipes.size() < nbMealToPrepare) {
                List<Recipe> recipesCandidates = new ArrayList<>();
                //recettes pas encore sélectionnées
                List<Recipe> recipesUnselected = recipes.stream().filter(c -> !menusRecipes.contains(c)).toList();

                //Recherche des recettes candidates parmi les recettes restantes avec le virtualstock
                //partiellement consommé par les recettes choisies
                for(Recipe recipe: recipesUnselected){
                    List<Ingredient> requiredIngredients = recipe.computeRequiredIngredients(nbPeople);
                    if(virtualStock.covers(requiredIngredients).covered()){
                        recipesCandidates.add(recipe);
                    }
                }

                //application des filtres utilisateur
                List<Recipe> filteredList = recipeFilteringService.applyFilters(recipesCandidates);
                //sortie de la boucle while.recherche recettes candidates si aucune recettes dispo apres filtre
                recipeCandidatesExist = !filteredList.isEmpty();

                if(recipeCandidatesExist){
                    //selection de la "meilleure" recette selon critères auto/critères utilisateur (?)
                    //Par défaut 1ère recette de la liste
                    Recipe selectedRecipe = recipeRankingService.selectBestRecipe(filteredList);

                    //mise à jour du stock virtuel
                    if(!virtualStock.consume(selectedRecipe.computeRequiredIngredients(nbPeople))){
                        throw new DomainException("mise à jour du stock virtuel impossible");
                    }

                    //Ajout de la recette à la liste des recettes du menu
                    menusRecipes.add(selectedRecipe);
                }
            }
            return menusRecipes;
        }
    }
