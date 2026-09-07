package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.user;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UserJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.user.UserMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUserRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    private final IUserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepository(IUserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserJpa findUserJpa(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new DomainException("error finding user"));
    }

    @Override
    public User findUserById(Long userId) {
        return userMapper.toDomain(userJpaRepository.findById(userId)
                .orElseThrow(() -> new DomainException("User not found")));
    }
}
