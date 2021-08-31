package propostas.propostas.dto;

import propostas.propostas.entities.Proposta;

public class PropostaDTOResponse {

    private Long id;
    private String email;
    private String nome;


    public PropostaDTOResponse() {
    }

    public PropostaDTOResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
}
