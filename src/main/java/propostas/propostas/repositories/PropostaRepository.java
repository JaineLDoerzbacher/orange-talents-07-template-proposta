package propostas.propostas.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import propostas.propostas.entities.Proposta;
import propostas.propostas.enuns.StatusProposta;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);


    @Query("select p from Proposta p where p.statusProposta = :status and p.cartao is null")
    List<Proposta> findByStatusDeCartaoElegivel(StatusProposta status);

}