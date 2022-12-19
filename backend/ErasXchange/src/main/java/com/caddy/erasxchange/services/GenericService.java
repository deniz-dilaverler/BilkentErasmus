package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * generic service is an abstract service that implements basic requests for other services to inherit
 * @param <T> is the base entity that the service will operate on
 * @param <Repository> class of the repository is also passed along to the parent (this class) to ensure
 * that the child classes can also use their specific repository class with special methods
 */
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

    /**
     * this is needed by the findById() method to throw errors with relevant naming
     * @return the name of the entity the service works on
     */
    protected abstract String getClassName();
}
