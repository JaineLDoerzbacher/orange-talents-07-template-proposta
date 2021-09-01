package propostas.propostas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import propostas.propostas.dto.BiometriaDTO;
import propostas.propostas.entities.Biometria;
import propostas.propostas.entities.Cartao;
import propostas.propostas.repositories.BiometriaRepository;
import propostas.propostas.repositories.CartaoRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/biometria")
public class BiometriaController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    BiometriaRepository biometriaRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> cadastroBiometria(@PathVariable("id") Long id,
                                               @RequestBody @Valid BiometriaDTO biometriaRequest,
                                               UriComponentsBuilder builder){

        Optional<Cartao> cartaoExistente = cartaoRepository.findById(id);

        if (cartaoExistente.isPresent()) {

            try {
                Biometria biometria = biometriaRequest.converteBiometria(cartaoExistente.get());
                biometriaRepository.save(biometria);
                URI uri = builder.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
                return ResponseEntity.created(uri).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).body("Biometria não está em Base64");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
