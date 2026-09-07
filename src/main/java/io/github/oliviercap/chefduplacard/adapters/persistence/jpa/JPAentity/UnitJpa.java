package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "unit"
)
public class UnitJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code",nullable = false, length = 250)
    private String symbol;

    protected UnitJpa() {
    }

    public UnitJpa(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public UnitJpa(
            Long id,
            String name,
            String symbol
    ) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
