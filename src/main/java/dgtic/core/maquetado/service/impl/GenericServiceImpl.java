package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID>{
    abstract JpaRepository<T, ID> getRepository();

    @Override
    @Transactional(readOnly = true)
    public T getById(ID id) {
        return getRepository().findById(id).orElseThrow(() -> new RuntimeException("No se encontro el Objeto con el id: "+id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    @Transactional
    public T create(T entity) {
        getRepository().save(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        if (!getRepository().existsById(id)) {
            return null;
        }
        return getRepository().save(entity);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        getRepository().deleteById(id);
    }
}
