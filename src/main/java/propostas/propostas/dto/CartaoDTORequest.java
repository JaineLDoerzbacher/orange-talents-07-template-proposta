package propostas.propostas.dto;

import propostas.propostas.entities.Cartao;
import propostas.propostas.entities.Proposta;
import propostas.propostas.repositories.PropostaRepository;

import java.time.LocalDateTime;

public class CartaoDTORequest {

    private String id;
    private String titular;
    private LocalDateTime emitidoEm;
    private Integer limite;
    private Long idProposta;

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Integer getLimite() {
        return limite;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public Cartao toModel(PropostaRepository propostaRepository) {
        Proposta proposta = propostaRepository.findById(idProposta).get();
        return new Cartao(id, emitidoEm, titular, limite, proposta);
    }
}
