package propostas.propostas.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class InformaStatusCartaoDTO {

    private String resultado;
    @JsonCreator
    public InformaStatusCartaoDTO(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

}
