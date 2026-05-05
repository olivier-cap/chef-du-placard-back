package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA entity.
 * Used to totally separate the domain and JPA
 * JPA fill this entity when a request is done.
 */
@Entity
@Table(
        name = "stock_line",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_stockline_stock_aliment",
                columnNames = {"stock_id", "aliment_id"}
        )
)
public class StockLineJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockJpa stockJpa;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "aliment_id", nullable = false)
    private AlimentJpa alimentJpa;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "unite_id", nullable = false)
    private UnitJpa unitJpa;

    @Column(name = "quantite", nullable = false)
    private BigDecimal quantity;

    protected StockLineJpa() {
    }

    public StockLineJpa(StockJpa stockJpa, AlimentJpa alimentJpa, UnitJpa unitJpa, BigDecimal quantity) {
        this.stockJpa = stockJpa;
        this.alimentJpa = alimentJpa;
        this.unitJpa = unitJpa;
        this.quantity = quantity;
    }

    /** Getters and Setters **/

    public Long getId() {
        return id;
    }

    public StockJpa getStockJpa() {
        return stockJpa;
    }

    public void setStockJpa(StockJpa stockJpa) {
        this.stockJpa = stockJpa;
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
