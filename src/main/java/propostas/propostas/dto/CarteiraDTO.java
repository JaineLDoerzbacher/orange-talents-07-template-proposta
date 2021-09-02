package propostas.propostas.dto;

import propostas.propostas.entities.Cartao;
import propostas.propostas.entities.Carteira;
import propostas.propostas.enuns.CarteiraTipo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraDTO {

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraTipo carteira;

    public CarteiraDTO(String email, CarteiraTipo carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    @Deprecated
    public CarteiraDTO() {
    }

    public String getEmail() {
        return email;
    }

    public CarteiraTipo getCarteira() {
        return carteira;
    }

    public Carteira converter(Cartao cartao) {
        return new Carteira(this.email, cartao, carteira);
    }
}
