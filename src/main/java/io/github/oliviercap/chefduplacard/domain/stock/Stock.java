package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.math.BigDecimal;
import java.util.*;

/**
 * Stock d'aliments.
 * Le stock est représenté par un ensemble de lignes aliment ↔ quantité.
 */
public final class Stock {
    //Pour permettre recherches par aliment, stockage des données dans map.
    private final Map<Aliment, StockLine> stockMap = new HashMap<>();
    private final String name;

    /**
     * Constructeur par défaut
     * @param stockLines
     */
    public Stock(String name, List<StockLine> stockLines) {
        if(name == null || name.isBlank()){
            throw new DomainException("stock name cannot be blank or null");
        }

        if(stockLines == null) {
            throw new DomainException("stock lines cannot be null");
        }

        this.name = name;

        for(StockLine stockLine : stockLines) {
            if(stockLine == null) {
                throw new DomainException("stock line cannot be null");
            }

            if(stockMap.containsKey(stockLine.getAliment())) {
                throw new DomainException("stock cannot contain duplicate aliment lines");
            }

            stockMap.put(stockLine.getAliment(), stockLine);
        }
    }

    /**
     * Vérifie si une liste d'ingrédients (aliment + quantité) est disponible dans le stock
     * @param requiredIngredients liste des ingrédients à tester
     * @return coveredIngredient, à true si tout est couvert, à false + liste non couverts sinon.
     */
    public CoveredIngredients covers(List<Ingredient> requiredIngredients){
        if (requiredIngredients == null) {
            throw new DomainException("required ingredients list must not be null");
        }

        if (requiredIngredients.isEmpty()) {
            throw new DomainException("required ingredients list must not be empty");
        }

        if (requiredIngredients.stream().anyMatch(Objects::isNull)) {
            throw new DomainException("required ingredients list must not contain null elements");
        }

        List<Ingredient> uncoveredIngredients = new ArrayList<>();
        CoveredIngredients coveredIngredients;
        boolean covered = true;

        List<Ingredient> aggregatedIngredients = aggregateQuantities(requiredIngredients);

        //Calcul ingrédients présents en quantité suffisante ou non
        for(Ingredient ingredient : aggregatedIngredients) {
            boolean alimentIsInStock = stockMap.containsKey(ingredient.getAliment());
            if(alimentIsInStock) {
                BigDecimal quantityStock = stockMap.get(ingredient.getAliment()).getQuantity();
                if(ingredient.getQuantity().compareTo(quantityStock) > 0) {
                    covered = false;
                    uncoveredIngredients.add(ingredient);
                }
            }
            else {
                covered = false;
                uncoveredIngredients.add(ingredient);
            }
        }

        coveredIngredients = new CoveredIngredients(covered, uncoveredIngredients);
        return coveredIngredients;
    }

    /*Créer une copie de ce stock pour obtenir une copie virtuelle
      Copie les lignes de stock une à une, les quantités dans la copie du stock
      sont indépendantes du stock d'origine
     */
    public Stock copyForSimulation(String nameCopy) {
        Objects.requireNonNull(nameCopy, "nameCopy must not be null");
        if(nameCopy.isBlank()){
            throw new IllegalArgumentException("nameCopy must not be blank");
        }

        return new Stock(
                nameCopy,
                this.getStockMap().values().stream()
                        .map(StockLine::copyForSimulation)
                        .toList()
        );
    }

    /*
    Déduit une certaine quantité d'un aliment.
    Retourne true si la consommation/déduction est possible et réalisée
    Retourne false si le stock est insuffisant, le stock est alors laissé intact
     */
    public boolean consume(List<Ingredient> ingredients) {
        if(ingredients == null) {
            throw new DomainException("ingredients list must not be null");
        }

        boolean sufficientStock = this.covers(ingredients).covered() ? true : false;

        //Changement : "consommation" TOUJOURS POSSIBLE
        //SI ingrédients en quantité insuffisante, Quantité fixée à 0. Dans ce cas, return false.

        List<Ingredient> aggregatedIngredients = aggregateQuantities(ingredients);

        for(Ingredient ingredient : aggregatedIngredients) {
            StockLine stockLine = this.stockMap.get(ingredient.getAliment());
            stockLine.subtractQuantity(
                    new StockLine(
                            ingredient.getQuantity(),
                            ingredient.getAliment(),
                            ingredient.getUnit()
                    )
            );
        }

        return sufficientStock;
        //}
        //Ingredient not covered by stock
        //return false;
    }

    /**
     * Ajoute une stockLine au stock actuel.
     * La stockline n'est pas sauvegardée dans la base à ce stade !
     * Passer par stockRepo.save si sauvegarde souhaitée
     * @param stockLine
     */
    public void addNewStockLine(StockLine stockLine) {
        if(stockLine == null) {
            throw new DomainException("stockLine must not be null");
        }
        if(!stockLine.check()) {
            throw new DomainException("StockLine is not correctly formed");
        }

        stockMap.put(stockLine.getAliment(), stockLine);
    }

    //Recherche des aliments identiques dans une liste
    //Creation d'une nouvelle liste où les quantités sont agrégées par ingrédients
    private List<Ingredient> aggregateQuantities(List<Ingredient> ingredients) {
        Map<Aliment, Ingredient> aggregatedIngredients = new HashMap<>();

        for (Ingredient ingredient : ingredients) {
            Aliment aliment = ingredient.getAliment();

            if (aggregatedIngredients.containsKey(aliment)) {
                aggregatedIngredients.get(aliment).addQuantityFrom(ingredient);
            } else {
                aggregatedIngredients.put(
                        aliment,
                        new Ingredient(
                                ingredient.getQuantity(),
                                ingredient.getAliment(),
                                ingredient.getUnit()
                        )
                );
            }
        }

        return aggregatedIngredients.values().stream().toList();
    }

    /** Getters and Setters **/
    public Map<Aliment, StockLine> getStockMap() {
        return Map.copyOf(stockMap);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stock stock)) return false;
        return Objects.equals(stockMap, stock.stockMap) && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockMap, name);
    }
}
