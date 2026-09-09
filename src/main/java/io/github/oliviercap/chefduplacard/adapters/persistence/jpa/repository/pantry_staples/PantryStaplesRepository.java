package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples.PantryStaplesMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples_line.PantryStaplesLineMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PantryStaplesRepository implements IPantryStaplesRepository {

    private final IPantryStaplesJpaRepository jpaRepository;
    private final PantryStaplesMapper pantryStaplesMapper;
    private final PantryStaplesLineMapper pantryStaplesLineMapper;

    public PantryStaplesRepository(
            IPantryStaplesJpaRepository jpaRepository,
            PantryStaplesMapper pantryStaplesMapper,
            PantryStaplesLineMapper pantryStaplesLineMapper
    ) {
        this.jpaRepository = jpaRepository;
        this.pantryStaplesMapper = pantryStaplesMapper;
        this.pantryStaplesLineMapper = pantryStaplesLineMapper;
    }


    @Override
    public PantryStaples findByUserId(Long userId) {
        return pantryStaplesMapper.toDomain(
                jpaRepository.findByUserId(userId)
                        .orElseThrow(
                                () -> new DomainException("error find pantry staples for user " + userId)
                        )
        );
    }
}
