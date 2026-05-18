package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlimentRepository implements IAlimentRepository {

    private final IAlimentJpaRepository alimentJpaRepository;
    private final AlimentMapper alimentMapper;

    public AlimentRepository(IAlimentJpaRepository alimentJpaRepository, AlimentMapper alimentMapper) {
        this.alimentJpaRepository = alimentJpaRepository;
        this.alimentMapper = alimentMapper;
    }

    @Override
    public List<Aliment> findAll() {
        return alimentJpaRepository.findAll().stream()
                .map(alimentMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Aliment> findAlimentByName(String name) {
        return alimentJpaRepository.findByName(name)
                .map(alimentMapper::toDomain);
    }

}
