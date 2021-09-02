package propostas.propostas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propostas.propostas.entities.Viagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {
}
