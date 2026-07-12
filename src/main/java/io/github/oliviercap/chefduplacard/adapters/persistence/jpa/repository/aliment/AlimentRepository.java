package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
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

    public List<AlimentJpa> findAllJpa() {
        return alimentJpaRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Aliment newAliment) {
        Objects.requireNonNull(newAliment);

        if(!newAliment.check()) {
            throw new DomainException("Incorrect aliment data");
        }

        alimentJpaRepository.save(alimentMapper.toEntity(newAliment));
    }

    @Transactional(readOnly = true)
    @Override
    public void modify(Aliment aliment, String newAlimentName, String newAlimentDescription) {
        Objects.requireNonNull(aliment);

        if(!aliment.check()) {
            throw new DomainException("Incorrect aliment data");
        }

        AlimentJpa alimentJpa = alimentJpaRepository.findByName(aliment.getName())
                .orElseThrow(() -> new DomainException("Aliment not found " + aliment.getName()));

        //Changement du nom
        if(!alimentJpa.getName().equals(newAlimentName)) {
            alimentJpa.setName(newAlimentName);
        }

        //Changement de la description
        if(!alimentJpa.getDescription().equals(newAlimentDescription)) {
            alimentJpa.setDescription(newAlimentDescription);
        }
    }

    @Override
    public Optional<AlimentJpa> findByName(String name) {
        return alimentJpaRepository.findByName(name);
    }

    @Override
    public AlimentJpa getReferenceById(Long id) {
        return alimentJpaRepository.getReferenceById(id);
    }

    @Override
    public Optional<AlimentJpa> findAlimentJpaById(Long id) {
        return alimentJpaRepository.findById(id);
    }


}
