package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples_line.PantryStaplesLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.user.UserMapper;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesId;
import org.springframework.stereotype.Component;

@Component
public class PantryStaplesMapper {

    private final UserMapper userMapper;
    private final PantryStaplesLineMapper pantryStaplesLineMapper;

    public PantryStaplesMapper(UserMapper userMapper, PantryStaplesLineMapper pantryStaplesLineMapper) {
        this.userMapper = userMapper;
        this.pantryStaplesLineMapper = pantryStaplesLineMapper;
    }

    public PantryStaples toDomain(PantryStaplesJpa pantryStaplesJpa) {
        return new PantryStaples(
                pantryStaplesJpa.isDefault(),
                userMapper.toDomain(pantryStaplesJpa.getUserJpa()),
                new PantryStaplesId(pantryStaplesJpa.getId()),
                pantryStaplesJpa.getPantryStaplesLineSet().stream()
                        .map(pantryStaplesLineMapper::toDomain)
                        .toList()
        );
    }
}
