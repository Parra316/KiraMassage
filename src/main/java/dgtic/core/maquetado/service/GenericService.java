package dgtic.core.maquetado.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<T, ID> {
    T getById(ID id);
    Page<T> findAll(Pageable pageable);
    List<T> findAll();
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
