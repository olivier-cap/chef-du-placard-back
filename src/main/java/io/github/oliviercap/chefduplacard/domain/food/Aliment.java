package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.aliment_type.AlimentType;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public final class Aliment {
    /*
    Identity decision:
    An Aliment is identified by a deterministic identifier computed from its
    current differentiating properties: name and description.
    The active flag does not participate in identity.
    If name or description changes, it represents a new Aliment instance
    with a different identifier.
    */

    private final AlimentId id;
    private String identifier;
    private String name;
    private String description;
    private boolean active;
    private Set<AlimentType> types;

    public Aliment(
            AlimentId id,
            String name,
            String description,
            boolean active,
            Set<AlimentType> types
    ) {
        if (id == null) {
            throw new DomainException("Aliment id must not be null");
        }

        validateName(name);

        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.identifier = computeIdentifier(name, description);
        this.types = types == null ? Set.of() : Set.copyOf(types);    }

    public Aliment(
            AlimentId id,
            String name,
            String description,
            boolean active
    ) {
        if (id == null) {
            throw new DomainException("Aliment id must not be null");
        }

        validateName(name);

        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.identifier = computeIdentifier(name, description);
    }

    public Aliment(
            String name,
            String description,
            boolean active
    ) {
        validateName(name);

        this.id = null;
        this.name = name;
        this.description = description;
        this.active = active;
        this.identifier = computeIdentifier(name, description);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException(
                    "Aliment name must not be null or blank"
            );
        }
    }


    private static String computeIdentifier(String name, String description) {
        String normalizedName = normalizeRequired(name);
        String normalizedDescription = normalizeOptional(description);
        String source = normalizedName + "|" + normalizedDescription;

        return sha256(source);
    }

    private static String normalizeRequired(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainException("Required value must not be null or blank");
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeOptional(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private static String sha256(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(source.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }

    /**
     * Check this aliment
     * @return true if this aliment is correctly formed
     */
    public boolean check() {
        return this.name != null && !name.isBlank();
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public AlimentId getId() {
        return id;
    }

    public Set<AlimentType> getTypes() {
        return Set.copyOf(types);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aliment aliment)) return false;
        return active == aliment.active && Objects.equals(id, aliment.id) && Objects.equals(identifier, aliment.identifier) && Objects.equals(name, aliment.name) && Objects.equals(description, aliment.description) && Objects.equals(types, aliment.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identifier, name, description, active, types);
    }
}