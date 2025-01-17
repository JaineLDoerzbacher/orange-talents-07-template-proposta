package propostas.propostas.entities;

import propostas.propostas.enuns.CarteiraTipo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @ManyToOne
    private Cartao cartao;

    @Enumerated(EnumType.STRING)
    private CarteiraTipo carteiraTipo;

    public Carteira(String email, Cartao cartao, CarteiraTipo carteiraTipo) {
        this.email = email;
        this.cartao = cartao;
        this.carteiraTipo = carteiraTipo;
    }

    @Deprecated
    public Carteira() {
    }

    public Long getId() {
        return id;
    }
}