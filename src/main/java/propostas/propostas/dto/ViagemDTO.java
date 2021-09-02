package propostas.propostas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import propostas.propostas.entities.Cartao;
import propostas.propostas.entities.Viagem;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ViagemDTO {

    @NotBlank
    private String destino;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate termino;

    @Deprecated
    public ViagemDTO() {
    }

    public ViagemDTO(String destino, LocalDate termino) {
        this.destino = destino;
        this.termino = termino;
    }

    public Viagem converte(String ipClient, String userAgent, Cartao cartao) {
        return new Viagem(this.destino, this.termino, ipClient, userAgent, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTermino() {
        return termino;
    }
}
