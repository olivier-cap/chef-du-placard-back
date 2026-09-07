package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UserJpa;
import io.github.oliviercap.chefduplacard.domain.user.User;

public interface IUserRepository {
    UserJpa findUserJpa(Long userId);
    User findUserById(Long userId);
}
