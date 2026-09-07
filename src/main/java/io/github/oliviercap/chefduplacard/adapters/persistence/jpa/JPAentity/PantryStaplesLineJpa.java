package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "pantry_staples_line",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_staple_line",
                columnNames = {"pantry_staples_id", "aliment_id"}
        )
)
public class PantryStaplesLineJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pantry_staples_id", nullable = false)
    private PantryStaplesJpa pantryStaplesJpa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aliment_id", nullable = false)
    private AlimentJpa alimentJpa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitJpa unitJpa;

    @Column(name = "quantity")
    private BigDecimal quantity;

    public PantryStaplesLineJpa() {
    }

    public PantryStaplesLineJpa(PantryStaplesJpa pantryStaplesJpa, AlimentJpa alimentJpa, UnitJpa unitJpa, BigDecimal quantity) {
        this.pantryStaplesJpa = pantryStaplesJpa;
        this.alimentJpa = alimentJpa;
        this.unitJpa = unitJpa;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public PantryStaplesJpa getPantryStaplesJpa() {
        return pantryStaplesJpa;
    }

    public void setPantryStaplesJpa(PantryStaplesJpa pantryStaplesJpa) {
        this.pantryStaplesJpa = pantryStaplesJpa;
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
