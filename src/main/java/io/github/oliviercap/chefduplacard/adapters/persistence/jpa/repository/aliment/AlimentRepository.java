package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlimentRepository implements IAlimentRepository{

    private final IAlimentJpaRepository alimentJpaRepository;
    private final IAlimentMapper alimentMapper;
    private final IAlimentJpaToDtoConverter alimentJpaToDtoConverter;

    public AlimentRepository(IAlimentJpaRepository alimentJpaRepository, IAlimentMapper alimentMapper,
                             IAlimentJpaToDtoConverter alimentJpaToDtoConverter) {
        this.alimentJpaRepository = alimentJpaRepository;
        this.alimentMapper = alimentMapper;
        this.alimentJpaToDtoConverter = alimentJpaToDtoConverter;
    }

    @Override
    public List<Aliment> findAll() {
        return alimentJpaRepository.findAll().stream()
                .map(alimentJpaToDtoConverter::toDTO)
                .map(alimentMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Aliment> findAlimentByName(String name) {
        return alimentJpaRepository.findByName(name)
                .map(alimentJpaToDtoConverter::toDTO)
                .map(alimentMapper::toDomain);
    }

}
