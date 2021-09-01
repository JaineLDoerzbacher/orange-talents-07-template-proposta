package propostas.propostas.controllers;

import feign.FeignException;
import io.opentracing.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import propostas.propostas.dto.AnalisaPropostaDto;
import propostas.propostas.dto.AnalisePropostaDTORequest;
import propostas.propostas.dto.PropostaDTORequest;
import propostas.propostas.entities.Proposta;
import propostas.propostas.enums.StatusProposta;
import propostas.propostas.repositories.PropostaRepository;
import propostas.propostas.swagger.ConsultaSwagger;
import propostas.propostas.validators.ApiErroException;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    ConsultaSwagger consultaSwagger;
    @PostMapping
    public ResponseEntity<PropostaDTORequest> cadastro(@RequestBody @Valid PropostaDTORequest PropostaDTORequest, UriComponentsBuilder uriBuilder) {


        //caso o documento exista, vai jogar uma exceção
        if (documentoExistente(PropostaDTORequest.getDocumento())) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é permitido mais de uma proposta por CPF/CNPJ");
        }
        //senão irá salvar no banco de dados
        Proposta proposta = PropostaDTORequest.converte();
        propostaRepository.save(proposta);


        //analisa a proposta para torná-la elegível ou não, em caso de restrições
        AnalisePropostaDTORequest AnalisePropostaDTORequest = new AnalisePropostaDTORequest(proposta.getDocumento(),
                proposta.getNome(), proposta.getId());
        try{
            AnalisaPropostaDto analisaPropostaDto = consultaSwagger.analiseProposta(AnalisePropostaDTORequest);
            proposta.atualizaStatusProposta(StatusProposta.ELEGIVEL);
        }catch (FeignException.UnprocessableEntity ex) {
            proposta.atualizaStatusProposta(StatusProposta.NAO_ELEGIVEL);
        }
        propostaRepository.save(proposta);


        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }



    //Verifica se existe documento no banco de dados
    private boolean documentoExistente(String documento) {
        return propostaRepository.findByDocumento(documento).isPresent();
    }

}