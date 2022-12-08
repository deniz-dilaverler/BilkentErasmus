package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<T extends BaseEntity, Repository extends JpaRepository<T, Long> > {
    protected  Repository repository;

    public GenericService(Repository repository) {
        this.repository = repository;
    }

    public T findById(Long id) {
        Optional<T> optional = repository.findById(id);

        if(optional.isEmpty()) throw new EntityNotFoundException();
        else return optional.get();

    }

    public void addEtity(T entity) {
        repository.save(entity);
    }

    public void addEntities(Iterable<T> entities) {
        repository.saveAll(entities);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void update(T entity) {


    }

}
