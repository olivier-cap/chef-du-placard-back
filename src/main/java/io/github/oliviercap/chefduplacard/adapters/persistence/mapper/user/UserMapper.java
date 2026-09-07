package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.user;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UserJpa;
import io.github.oliviercap.chefduplacard.domain.user.User;
import io.github.oliviercap.chefduplacard.domain.user.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserJpa userJpa) {
        return new User(
                new UserId(userJpa.getId()),
                userJpa.getPseudo(),
                userJpa.getEmail(),
                userJpa.isAdmin()
        );
    }
}
