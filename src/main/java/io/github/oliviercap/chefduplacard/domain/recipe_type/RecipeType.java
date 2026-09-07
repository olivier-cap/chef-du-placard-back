package io.github.oliviercap.chefduplacard.domain.recipe_type;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.Objects;

public class RecipeType {
    private final RecipeTypeId id;
    private String name;

    public RecipeType(RecipeTypeId id, String name) {
        if(id == null) {
            throw new DomainException("Recipetype id must not be null");
        }

        this.id = id;
        this.name = name;
    }

    public RecipeTypeId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RecipeType that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
