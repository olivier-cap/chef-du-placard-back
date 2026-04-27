package io.github.oliviercap.chefduplacard.domain.food;

/**
 * Represents an eatable food.
 */
public class Aliment {
    private String identifier;
    private String name;
    private String description;
    private Boolean active;

    public Aliment(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
