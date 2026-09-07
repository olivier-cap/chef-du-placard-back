package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "recipe",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class RecipeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "difficulty")
    private String difficulty;


    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "recipeJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<IngredientJpa> ingredients = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_type_id")
    private RecipeTypeJpa recipeTypeJpa;


    protected RecipeJpa() {}

    public RecipeJpa(String name, String instructions, int duration_minutes, String difficulty) {
        this.name = name;
        this.instructions = instructions;
        this.durationMinutes = duration_minutes;
        this.difficulty = difficulty;
    }

    public RecipeJpa(
            Long id,
            String name,
            String instructions,
            int durationMinutes,
            String difficulty
    ) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.durationMinutes = durationMinutes;
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

    public Set<IngredientJpa> getIngredients() {
        return Set.copyOf(ingredients);
    }

    public RecipeTypeJpa getRecipeTypeJpa() {
        return recipeTypeJpa;
    }

    void setRecipeTypeJpa(RecipeTypeJpa recipeTypeJpa) {
        this.recipeTypeJpa = recipeTypeJpa;
    }

}
