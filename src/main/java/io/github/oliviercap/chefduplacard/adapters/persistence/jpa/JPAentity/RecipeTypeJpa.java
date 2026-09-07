package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(
        name = "recipe_type"
)
public class RecipeTypeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "recipeTypeJpa")
    private List<RecipeJpa> recipeJpa = new ArrayList<>();

    public RecipeTypeJpa() {
    }

    public RecipeTypeJpa(String name, List<RecipeJpa> recipeJpa) {
        this.name = name;
        this.recipeJpa = recipeJpa;
    }

    public RecipeTypeJpa(String name) {
        this.name = name;
    }

    public RecipeTypeJpa(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addRecipe(RecipeJpa recipeJpa) {
        Objects.requireNonNull(recipeJpa, "recipeJpa must not be null");

        this.recipeJpa.add(recipeJpa);
        recipeJpa.setRecipeTypeJpa(this);
    }

    public Long getId() {
        return id;
    }

    public List<RecipeJpa> getRecipeJpa() {
        return List.copyOf(recipeJpa);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
