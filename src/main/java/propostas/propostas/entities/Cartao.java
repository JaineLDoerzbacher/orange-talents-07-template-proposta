package propostas.propostas.entities;

import propostas.propostas.enuns.StatusCartao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    @Entity
    public class Cartao {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String numeroCartao;
        private LocalDateTime emitidoEm = LocalDateTime.now();
        private String titular;
        private Integer limite;

        @OneToOne(mappedBy = "cartao")
        private Proposta proposta;


        @Enumerated(EnumType.STRING)
        private StatusCartao statusCartao = StatusCartao.ATIVO;

        @Deprecated
        public Cartao() {
        }

        public Cartao(String numeroCartao, LocalDateTime emitidoEm,
                      String titular, Integer limite, Proposta proposta) {
            this.numeroCartao = numeroCartao;
            this.emitidoEm = emitidoEm;
            this.titular = titular;
            this.limite = limite;
            this.proposta = proposta;
        }

        public Proposta getProposta() {
            return proposta;
        }

        public Long getId() {
            return id;
        }

        public String getNumeroCartao() {
            return numeroCartao;
        }

        public StatusCartao getStatusCartao() {
            return statusCartao;
        }



}
