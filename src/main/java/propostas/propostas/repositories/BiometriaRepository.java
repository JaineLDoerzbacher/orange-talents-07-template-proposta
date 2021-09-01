package propostas.propostas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propostas.propostas.entities.Biometria;

@Repository
public interface BiometriaRepository extends JpaRepository<Biometria, Long> {
}
