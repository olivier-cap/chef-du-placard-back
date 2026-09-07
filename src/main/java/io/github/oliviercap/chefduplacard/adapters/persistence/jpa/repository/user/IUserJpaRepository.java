package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.user;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserJpaRepository extends JpaRepository<UserJpa, Long> {
}
