package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "recette",
        uniqueConstraints = @UniqueConstraint(columnNames = "nom")
)
public class RecipeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 200)
    private String name;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "duree_minutes")
    private Integer durationMinutes;

    @Column(name = "difficulte", length = 50)
    private String difficulty;


    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "recipeJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IngredientJpa> ingredients = new ArrayList<>();

    protected RecipeJpa() {

    }

    public RecipeJpa(String name, String instructions, int duration_minutes, String difficulty) {
        this.name = name;
        this.instructions = instructions;
        this.durationMinutes = duration_minutes;
        this.difficulty = difficulty;
    }

    public void addIngredient(IngredientJpa ingredientJpa) {
        this.ingredients.add(ingredientJpa);
        ingredientJpa.setRecipeJpa(this);
    }

    public void removeIngredient(IngredientJpa ingredientJpa) {
        this.ingredients.remove(ingredientJpa);
        ingredientJpa.setRecipeJpa(null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer duration_minutes) {
        this.durationMinutes = duration_minutes;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<IngredientJpa> getIngredients() {
        return ingredients;
    }

}
