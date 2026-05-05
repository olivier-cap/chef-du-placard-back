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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockJpa")
    private List<StockLineJpa> stockLineJpa = new ArrayList<>();


    public StockJpa() {
    }

    public StockJpa(String name, List<StockLineJpa> stockLineJpa) {
        this.name = name;
        this.stockLineJpa = stockLineJpa;
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

    public void setStockLineJpa(List<StockLineJpa> stockLineJpa) {
        this.stockLineJpa = stockLineJpa;
    }
}
