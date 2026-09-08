package io.github.oliviercap.chefduplacard.domain.shopping_list;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingList {

    private ShoppingListId id;
    User user;
    LocalDate date;
    List<ShoppingListLine> lineList;

    public ShoppingList(ShoppingListId id, User user, LocalDate date, List<ShoppingListLine> lineList) {
        if (id == null) {
            throw new DomainException("shopping list id must not be null");
        }
        if (user == null) {
            throw new DomainException("user must not be null");
        }
        if (date == null) {
            throw new DomainException("date must not be null");
        }
        if (lineList == null) {
            throw new DomainException("lineList must not be null");
        }
        this.id = id;
        this.user = user;
        this.date = date;
        this.lineList = lineList;
    }

    public ShoppingListId getId() {
        return id;
    }

    public List<ShoppingListLine> getLineList() {
        return List.copyOf(lineList);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ShoppingList that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(date, that.date) && Objects.equals(lineList, that.lineList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, lineList);
    }
}
