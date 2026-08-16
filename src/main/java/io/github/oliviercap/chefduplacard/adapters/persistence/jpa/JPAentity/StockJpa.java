package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity.
 * Used to totally separate the domain and JPA
 * JPA fill this entity when a request is done.
 */
@Entity
@Table(
        name = "stock"
)
public class StockJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false ,length = 100)
    private String name;

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

    public StockJpa(String name) {
        this.name = name;
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

}
