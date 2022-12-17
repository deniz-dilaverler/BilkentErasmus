package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<T extends BaseEntity, Repository extends JpaRepository<T, Long> > {
    protected  Repository repository;

    public GenericService(Repository repository) {
        this.repository = repository;
    }



    public void saveEtity(T entity) {
        repository.save(entity);
    }

    public void saveEntities(Iterable<T> entities) {
        repository.saveAll(entities);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public T findById(Long id) {
        if (id == null ) throw new NullPointerException(getClassName() + " the id value can't be null");
        Optional<T> optional = repository.findById(id);

        if (optional.isEmpty()) throw new ResourceNotFoundException(getClassName() +  " with id: " + id + ",  not found") ;
        else return optional.get();
    }

    //this is needed by the findById() method to throw errors with relevant naming
    protected abstract String getClassName();
}
