package propostas.propostas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import propostas.propostas.dto.CarteiraDTO;
import propostas.propostas.dto.ResultadoCarteiraDTO;
import propostas.propostas.entities.Cartao;
import propostas.propostas.entities.Carteira;
import propostas.propostas.feign.CartaoFeign;
import propostas.propostas.repositories.CartaoRepository;
import propostas.propostas.repositories.CarteiraRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    CartaoFeign cartaoFeign;

    @PostMapping("{numeroCartao}")
    public ResponseEntity<?> associaCarteira(@PathVariable String numeroCartao,
                                             @RequestBody @Valid CarteiraDTO carteiraDTO,
                                             UriComponentsBuilder builder) {

        Optional<Cartao> associarCartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if (associarCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = associarCartao.get();

        try {

            ResultadoCarteiraDTO resultadoCarteiraDTO = cartaoFeign.associaCarteira(cartao.getNumeroCartao(),
                    new CarteiraDTO(carteiraDTO.getEmail(),
                            carteiraDTO.getCarteira()));
            System.out.println(resultadoCarteiraDTO.getResultado());

            Carteira carteira = carteiraDTO.converter(cartao);
            carteiraRepository.save(carteira);
            URI uri = builder.path("/carteiras/{id}/{idCarteira}").buildAndExpand(cartao.getId(), carteira.getId()).toUri();
            return ResponseEntity.created(uri).build();

        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body("Não foi possível fazer a associação do cartão");
        }
    }
}