package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name="shopping_list_line"
)
public class ShoppingListLineJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingListJpa shoppingListJpa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aliment_id", nullable = false)
    private AlimentJpa alimentJpa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitJpa unitJpa;

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    public ShoppingListLineJpa() {
    }

    public ShoppingListLineJpa(ShoppingListJpa shoppingListJpa, AlimentJpa alimentJpa, UnitJpa unitJpa, BigDecimal quantity) {
        this.shoppingListJpa = shoppingListJpa;
        this.alimentJpa = alimentJpa;
        this.unitJpa = unitJpa;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public ShoppingListJpa getShoppingListJpa() {
        return shoppingListJpa;
    }

    public void setShoppingListJpa(ShoppingListJpa shoppingListJpa) {
        this.shoppingListJpa = shoppingListJpa;
    }

    public AlimentJpa getAlimentJpa() {
        return alimentJpa;
    }

    public void setAlimentJpa(AlimentJpa alimentJpa) {
        this.alimentJpa = alimentJpa;
    }

    public UnitJpa getUnitJpa() {
        return unitJpa;
    }

    public void setUnitJpa(UnitJpa unitJpa) {
        this.unitJpa = unitJpa;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
