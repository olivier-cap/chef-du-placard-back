package io.github.oliviercap.chefduplacard.domain.pantry_staples;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PantryStaples {

    private final PantryStaplesId id;
    private User user;
    private boolean is_default;
    private Map<Aliment, PantryStaplesLine> pantryStaplesLineMap = new HashMap<>();

    public PantryStaples(boolean is_default, User user, PantryStaplesId id, List<PantryStaplesLine> pantryStaplesLineList) {
        if (id == null) {
            throw new DomainException("PantryStaples id must not be null");
        }
        if (user == null) {
            throw new DomainException("PantryStaples user must not be null");
        }
        this.is_default = is_default;
        this.user = user;
        this.id = id;

        if(pantryStaplesLineList == null) {
            throw new DomainException("pantrystaple lines cannot be null");
        }

        for(PantryStaplesLine pantryStaplesLine : pantryStaplesLineList) {
            if(pantryStaplesLine == null) {
                throw new DomainException("stock line cannot be null");
            }

            if(pantryStaplesLineMap.containsKey(pantryStaplesLine.getAliment())) {
                throw new DomainException("pantrystaple cannot contain duplicate aliment lines");
            }

            pantryStaplesLineMap.put(pantryStaplesLine.getAliment(), pantryStaplesLine);
        }
    }

    public PantryStaplesId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public Map<Aliment, PantryStaplesLine> getPantryStaplesLineMap() {
        return Map.copyOf(pantryStaplesLineMap);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PantryStaples that)) return false;
        return is_default == that.is_default && Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(pantryStaplesLineMap, that.pantryStaplesLineMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, is_default, pantryStaplesLineMap);
    }
}
