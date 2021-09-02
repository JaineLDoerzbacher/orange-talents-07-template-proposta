package propostas.propostas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propostas.propostas.entities.Carteira;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}