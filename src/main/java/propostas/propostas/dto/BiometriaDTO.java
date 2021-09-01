package propostas.propostas.dto;

import propostas.propostas.entities.Biometria;
import propostas.propostas.entities.Cartao;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaDTO {

    @NotBlank
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometria converteBiometria(Cartao cartao) {

        Base64.getDecoder().decode(this.fingerprint);
        return new Biometria(cartao, getFingerprint());
    }
}
