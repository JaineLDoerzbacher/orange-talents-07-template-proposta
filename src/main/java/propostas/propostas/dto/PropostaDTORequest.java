package propostas.propostas.dto;

import propostas.propostas.entities.Proposta;
import propostas.propostas.validators.CPFouCNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaDTORequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    @CPFouCNPJ
    private String documento;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;


    public PropostaDTORequest(String nome, String email, String documento, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta converte() {
        return new Proposta(this.nome, this.email, this.documento, this.endereco, this.salario);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}