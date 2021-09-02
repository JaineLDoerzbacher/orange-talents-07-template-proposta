package propostas.propostas.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ResultadoAvisoViagemDTO {

    private String resultado;

    @JsonCreator
    public ResultadoAvisoViagemDTO(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
