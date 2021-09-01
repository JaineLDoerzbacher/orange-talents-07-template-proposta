package propostas.propostas.dto;

import propostas.propostas.entities.Proposta;
import propostas.propostas.enuns.StatusProposta;

public class PropostaDTO {

    private String documento;
    private String email;
    private String nome;
    private StatusProposta statusProposta;


    public PropostaDTO(Proposta proposta) {
        documento = proposta.getDocumento();
        email = proposta.getEmail();
        nome = proposta.getNome();
        statusProposta = proposta.getStatusProposta();
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }
}
