    package io.github.oliviercap.chefduplacard.application.menu.cookablemenus;

    import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.ports.ICookableMenusInputPort;
    import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.ports.ICookableMenusOutputPort;
    import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
    import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
    import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
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

            CookableMenus response = findCookableMenus(
                    cookableMenusRequestModel.stockId(),
                    cookableMenusRequestModel.nbMealToPrepare(),
                    cookableMenusRequestModel.nbPeople(),
                    cookableMenusRequestModel.recipeFilters()
            );

            List<Recipe> menu = response.recipesStockOk();
            List<Recipe> proposal = response.recipesNoStock();

            String message;
            if (menu.size() == cookableMenusRequestModel.nbMealToPrepare()) {
                message = "nbmeal recipes founded";
            } else if (!menu.isEmpty()) {
                message = "insufficient stock or filters";
            } else {
                message = "no recipe found";
            }

            CookableMenusResponseModel responseModel = new CookableMenusResponseModel(
                    menu.size() == cookableMenusRequestModel.nbMealToPrepare(),
                    menu.stream()
                            .map(RecipeResponse::from)
                            .toList(),
                    proposal.stream().map(RecipeResponse::from).toList(),
                    message
            );

            cookableMenusOutputPort.displayCookableMenus(responseModel);
        }


//        private void findCookableMenusWithDate(CookableMenusRequestModelDate requestModelDate) {
//            //On traite un par un des ensembles <Jour(date), repas1(?), repas2(?), repas3(?), nbPersonnes>, triés par date
//            //On stocke dans une liste les ids des recettes déjà proposées.
//            //Tant qu'une autre recette est disponible, on en prend une différente.
//            //Si pas d'autre disponible, on recherche parmi toutes les recettes.
//            //Sinon, prendre la 1er recette parmi les non disponibles et dire "aliment(s) manquant(s)
//
//            Long stockId = requestModelDate.stockId();
//
//            Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new DomainException("Stock not found"));
//            VirtualStockFactory virtualStockFactory = new VirtualStockFactory();
//            VirtualStock virtualStock = virtualStockFactory.createForMenuPreparation(stock);//Utilisation du virtualstock exclusivement ici
//
//
//            //tri de la liste des repas demandés par date croissante
//            List<CookableMenusRequestModelDate.menuPerDayRequest> menuPerDayRequestList = requestModelDate.menuPerDayRequestList();
//            menuPerDayRequestList.sort(Comparator.comparing(CookableMenusRequestModelDate.menuPerDayRequest::date));
//
//            List<Recipe> alreadyDone = new ArrayList<>();
//
//            List<Recipe> recipes = recipeRepository.findAll();
//            boolean recipeCandidatesExist = !recipes.isEmpty();
//
//            List<Recipe> breakfasts = recipes.stream().filter(r -> r.getType().getName().equalsIgnoreCase("breakfast")).toList();
//            List<Recipe> lunchs = recipes.stream().filter(r -> r.getType().getName().equalsIgnoreCase("lunch")).toList();
//            List<Recipe> diner = recipes.stream().filter(r -> r.getType().getName().equalsIgnoreCase("diner")).toList();
//
//            List<MenuLine> menuLines = new ArrayList<>();
//
//            //recherche des repas demandés.
//            //si aucun repas possible avec le stock, proposition recette hors stock.
//            //enregistre pour chaque recette si stock/pas stock pour affichage utilisateur
//            if(recipeCandidatesExist) {
//                for (CookableMenusRequestModelDate.menuPerDayRequest request : menuPerDayRequestList) {
//                    if(request.breakfast() && !breakfasts.isEmpty()) {
//                        searchRecipe(breakfasts, alreadyDone, virtualStock, request.nbPeople());
//                        menuLines.add(
//                                new MenuLine()
//                        )
//                    }
//
//                }
//            }
//
//        }
//
//        private Recipe searchRecipe(List<Recipe> candidates, List<Recipe> alreadyDone, VirtualStock virtualStock, Integer nbPeople) {
//            //toutes les recettes de ce type ont déjà été proposées, on propose la 1er de la liste
//            if (alreadyDone.containsAll(candidates)) {
//                return candidates.getFirst();
//            }
//
//            //recherche d'une recette faisable avec le stock
//            for(Recipe candidate : candidates) {
//                List<Ingredient> requiredIngredients = candidate.computeRequiredIngredients(nbPeople);
//                if(virtualStock.covers(requiredIngredients).covered() && !alreadyDone.contains(candidate)){
//                    virtualStock.consume(requiredIngredients);
//                    return candidate;
//                }
//            }
//
//            //Pas de recettes faisables avec le stock, on propose la 1er recette non faisable avec le stock et non faite
//            return candidates.stream().filter(c -> !alreadyDone.contains(c)).findFirst()
//                    .orElseThrow(() -> new DomainException("impossible to select a recipe"));
//        }


        /**
         * Recherche des recettes réalisables pour nbPeople personnes, en fonction des filtres choisis.
         * Recherche au mieux nbMealToPrepare recettes
         * @param stockId
         * @param nbMealToPrepare
         * @param nbPeople
         * @return
         */
        //private List<Recipe> findCookableMenus(Long stockId, int nbMealToPrepare, int nbPeople, List<RecipeFilter> recipeFilters) {
         private CookableMenus findCookableMenus(Long stockId, int nbMealToPrepare, int nbPeople, List<RecipeFilter> recipeFilters) {

                List<Recipe> menusRecipes = new ArrayList<>(); //liste des recettes du menu
            List<Recipe> menusRecipesNoStock = new ArrayList<>(); //liste des recettes du menu


            Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new DomainException("Stock not found"));
            VirtualStockFactory virtualStockFactory = new VirtualStockFactory();
            VirtualStock virtualStock = virtualStockFactory.createForMenuPreparation(stock);//Utilisation du virtualstock exclusivement ici

            List<Recipe> recipes = recipeRepository.findAll();
            boolean recipeCandidatesExist = !recipes.isEmpty();

            //tant qu'il reste des recettes pour lesquelles on vérifie que le stock est disponible
            //et tant qu'on n'a pas encore trouvé le nb de recettes demandé
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
                    //selection de la "meilleure" recette
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
            if(menusRecipes.size() < nbMealToPrepare && !recipes.isEmpty()) {
                int nbMealRestant = nbMealToPrepare - menusRecipes.size();
                for(int i=0; i < nbMealRestant; i++) {
                    if(recipes.size() > i) {
                        menusRecipesNoStock.add(recipes.get(i));
                    } else {
                        menusRecipesNoStock.add(recipes.getFirst());
                    }
                }
            }

            CookableMenus result = new CookableMenus(menusRecipes, menusRecipesNoStock);
            return result;

            //return menusRecipes;
        }
    }
