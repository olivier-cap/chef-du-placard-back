package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Objects;

public final class Aliment {
    /*
    Identity decision:
    An Aliment is identified by a deterministic identifier computed from its
    current differentiating properties: name and description.
    The active flag does not participate in identity.
    If name or description changes, it represents a new Aliment instance
    with a different identifier.
    */

    private final String identifier;
    private final String name;
    private final String description;
    private final boolean active;

    public Aliment(String name, String description, boolean active) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Aliment name must not be null or blank");
        }

        this.name = name;
        this.description = description;
        this.active = active;
        this.identifier = computeIdentifier(name, description);
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aliment aliment)) {
            return false;
        }
        return Objects.equals(identifier, aliment.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}