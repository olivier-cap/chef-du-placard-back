package io.github.oliviercap.chefduplacard.domain.food;

import java.util.Objects;

/**
 * Represents an eatable food.
 */
public class Aliment {
    private String identifier;
    private String name;
    private String description;
    private Boolean active;

    public Aliment(String name, String description, boolean active) {
        this.description = description;
        this.name = name;
        this.active = active;
    }

    /** Getters and Setters **/

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aliment aliment)) return false;
        return Objects.equals(name, aliment.name) && Objects.equals(description, aliment.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
