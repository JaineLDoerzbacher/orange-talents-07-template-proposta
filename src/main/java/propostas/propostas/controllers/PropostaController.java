package propostas.propostas.controllers;

import antlr.debug.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import propostas.propostas.dto.PropostaDTORequest;
import propostas.propostas.entities.Proposta;
import propostas.propostas.repositories.PropostaRepository;
import propostas.propostas.validators.ApiErroException;
import propostas.propostas.validators.TratamentoDeErro;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    Tracer tracer;

    @PostMapping
    public ResponseEntity<PropostaDTORequest> cadastro(@RequestBody @Valid PropostaDTORequest PropostaDTORequest, UriComponentsBuilder uriBuilder) {


        //caso o documento exista, vai jogar uma exceção
        if (documentoExistente(PropostaDTORequest.getDocumento())) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é permitido mais de uma proposta por CPF/CNPJ");
        }
        //senão irá salvar no banco de dados
        Proposta proposta = PropostaDTORequest.converte();
        propostaRepository.save(proposta);


        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    //Verifica se existe documento no banco de dados
    private boolean documentoExistente(String documento) {
        return propostaRepository.findByDocumento(documento).isPresent();
    }

}