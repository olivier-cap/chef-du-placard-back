package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA entity.
 * Used to totally separate the domain and JPA
 * JPA fill this entity when a request is done.
 */
@Entity
@Table(
        name = "stock",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_stock_user",
                columnNames = {"user_id"}
        )
)
public class StockJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpa userJpa;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "stockJpa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StockLineJpa> stockLineJpa = new ArrayList<>();


    protected StockJpa() {
    }

    public StockJpa(
            String name,
            List<StockLineJpa> stockLineJpa
    ) {
        this.name = name;
        this.stockLineJpa = new ArrayList<>();

        if (stockLineJpa != null) {
            stockLineJpa.forEach(this::addStockLine);
        }
    }

    public StockJpa(
            Long id,
            String name,
            List<StockLineJpa> stockLineJpa
    ) {
        this.id = id;
        this.name = name;
        this.stockLineJpa = new ArrayList<>();

        if (stockLineJpa != null) {
            stockLineJpa.forEach(this::addStockLine);
        }
    }

    public StockJpa(String name, UserJpa userJpa, List<StockLineJpa> stockLineJpa) {
        this.name = name;
        this.userJpa = userJpa;
        this.stockLineJpa = stockLineJpa;
    }

    public StockJpa(String name, UserJpa userJpa) {
        this.name = name;
        this.userJpa = userJpa;
    }

    public void setUserJpa(UserJpa userJpa) {
        this.userJpa = userJpa;
    }


    public void addStockLine(StockLineJpa stockLine) {
        stockLineJpa.add(stockLine);
        stockLine.setStockJpa(this);
    }

    public void removeStockLine(StockLineJpa stockLine) {
        stockLineJpa.remove(stockLine);
        stockLine.setStockJpa(null);
    }
    /** Getters and Setters **/

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockLineJpa> getStockLineJpa() {
        return stockLineJpa;
    }

    public UserJpa getUserJpa() {
        return userJpa;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockJpa stockJpa)) return false;
        return Objects.equals(id, stockJpa.id) && Objects.equals(name, stockJpa.name) && Objects.equals(userJpa, stockJpa.userJpa) && Objects.equals(stockLineJpa, stockJpa.stockLineJpa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userJpa, stockLineJpa);
    }
}
