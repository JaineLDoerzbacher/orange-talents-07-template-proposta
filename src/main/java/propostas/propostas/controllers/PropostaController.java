package propostas.propostas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import propostas.propostas.dto.PropostaDTORequest;
import propostas.propostas.entities.Proposta;
import propostas.propostas.repositories.PropostaRepository;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<PropostaDTORequest> cadastro(@RequestBody @Valid PropostaDTORequest propostaRequest,
                                                    UriComponentsBuilder uriBuilder) {
        Proposta proposta = propostaRequest.converte();
        propostaRepository.save(proposta);

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }


}