package propostas.propostas.dto;

public class ResultadoCarteiraDTO {

    private String resultado;
    private String id;


    public ResultadoCarteiraDTO(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    @Deprecated
    public ResultadoCarteiraDTO() {
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
