package dgtic.core.maquetado.repository;

import dgtic.core.maquetado.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
}
