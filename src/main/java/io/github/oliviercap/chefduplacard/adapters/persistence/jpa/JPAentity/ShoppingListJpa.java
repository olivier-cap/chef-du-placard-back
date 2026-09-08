package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name="shopping_list"
)
public class ShoppingListJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private UserJpa userJpa;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "shoppingListJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ShoppingListLineJpa> shoppingListLineJpaList = new ArrayList<>();

    @Column(name="date")
    private LocalDate date;

    public ShoppingListJpa() {
    }

    public ShoppingListJpa(UserJpa userJpa, List<ShoppingListLineJpa> shoppingListLineJpaList, LocalDate date) {
        this.userJpa = userJpa;
        this.shoppingListLineJpaList = shoppingListLineJpaList;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public UserJpa getUserJpa() {
        return userJpa;
    }

    public void setUserJpa(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    public List<ShoppingListLineJpa> getShoppingListLineJpaList() {
        return List.copyOf(shoppingListLineJpaList);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
