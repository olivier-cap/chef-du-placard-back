package io.github.oliviercap.chefduplacard.domain.aliment_type;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.Objects;

public class AlimentType {

    private final AlimentTypeId id;
    private String name;

    public AlimentType(String name, AlimentTypeId id) {
        if(id == null) {
            throw new DomainException("aliment id must not be null");
        }
        if(name == null || name.isBlank()) {
            throw new DomainException("aliment type name must not be null or blank");
        }
        this.name = name;
        this.id = id;
    }

    public AlimentTypeId getId() {
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
        if (!(o instanceof AlimentType that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
