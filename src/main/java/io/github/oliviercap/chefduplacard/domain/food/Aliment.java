package io.github.oliviercap.chefduplacard.domain.food;

/**
 * Represents an eatable food.
 */
public class Aliment {
    private String identifier;
    private String name;
    private String description;
    private Boolean active;

    public Aliment(String description, String name, boolean active) {
        this.description = description;
        this.name = name;
        this.active = active;
    }

    public Aliment(String identifier, String name, String description, Boolean active) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.active = active;
    }
}
