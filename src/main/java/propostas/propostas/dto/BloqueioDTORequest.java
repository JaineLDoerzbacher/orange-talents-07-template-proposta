package propostas.propostas.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloqueioDTORequest {

    private String sistemaResponsavel;

    @JsonCreator
    public BloqueioDTORequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
