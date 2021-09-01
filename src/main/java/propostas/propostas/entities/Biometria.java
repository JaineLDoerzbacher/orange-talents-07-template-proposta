package propostas.propostas.entities;

import javax.persistence.*;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cartao cartao;

    @Lob
    private String fingerprint;

    @Deprecated
    public Biometria() {
    }

    public Biometria(Cartao cartao, String fingerprint) {
        this.cartao = cartao;
        this.fingerprint = fingerprint;
    }

    public Long getId() {
        return id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
