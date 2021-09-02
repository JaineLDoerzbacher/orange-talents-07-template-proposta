package propostas.propostas.controllers;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propostas.propostas.dto.ResultadoAvisoViagemDTO;
import propostas.propostas.dto.ViagemDTO;
import propostas.propostas.entities.Cartao;
import propostas.propostas.entities.Viagem;
import propostas.propostas.feign.CartaoFeign;
import propostas.propostas.repositories.CartaoRepository;
import propostas.propostas.repositories.ViagemRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/viagem")
public class ViagemController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    ViagemRepository viagemRepository;

    @Autowired
    CartaoFeign cartaoFeign;

    @PostMapping("/{cartao}")
    public ResponseEntity<?> cadastraAvisoDeViagem(@PathVariable String cartao,
                                                   @RequestBody @Valid ViagemDTO viagemDTO,
                                                   HttpServletRequest http) {

        Optional<Cartao> cartaoViagem = cartaoRepository.findByNumeroCartao(cartao);

        if (cartaoViagem.isEmpty()) {
            return ResponseEntity.status(404).body("Cartão não encontrado");
        }
        Cartao verificaCartao = cartaoViagem.get();
        if (verificaCartao.estaBloqueado()) {
            return ResponseEntity.status(422).body("Cartão bloqueado");
        }

        String ipClient = http.getRemoteAddr();
        String userAgent = http.getHeader("User-Agent");

        try {
            ResultadoAvisoViagemDTO notificacao = cartaoFeign.notificaViagem(cartao, new ViagemDTO(viagemDTO.getDestino(), viagemDTO.getValidoAte()));
            System.out.println(notificacao.getResultado());
            Viagem avisoViagem = viagemDTO.converte(ipClient, userAgent, verificaCartao);
            viagemRepository.save(avisoViagem);
            return ResponseEntity.status(200).build();

        } catch (FeignException error) {
            error.printStackTrace();
            return ResponseEntity.status(422).body("Não foi possível realizar a notificação de viagem.");
        }
    }
}