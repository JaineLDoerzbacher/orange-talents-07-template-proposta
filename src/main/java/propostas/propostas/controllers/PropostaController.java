package propostas.propostas.controllers;

import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import propostas.propostas.dto.AnalisaPropostaDTO;
import propostas.propostas.dto.AnalisePropostaDTORequest;
import propostas.propostas.dto.PropostaDTO;
import propostas.propostas.dto.PropostaDTORequest;
import propostas.propostas.entities.Proposta;
import propostas.propostas.enuns.StatusProposta;
import propostas.propostas.metricas.MetricasPropostas;
import propostas.propostas.repositories.PropostaRepository;
import propostas.propostas.swagger.ConsultaSwagger;
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
    ConsultaSwagger consultaSwagger;

    @Autowired
    MetricasPropostas metricasPropostas;

    @Autowired
    Tracer tracer;

    @PostMapping
    public ResponseEntity<PropostaDTORequest> cadastro(@RequestBody @Valid PropostaDTORequest propostaDTORequest, UriComponentsBuilder uriBuilder) {

        Span activeSpan = tracer.activeSpan();

        //caso o documento exista, vai jogar uma exceção
        if (documentoExistente(propostaDTORequest.getDocumento())) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é permitido mais de uma proposta por CPF/CNPJ");
        }
        //senão irá salvar no banco de dados
        Proposta proposta = propostaDTORequest.converte();
        propostaRepository.save(proposta);
        metricasPropostas.incrementa();
        activeSpan.setBaggageItem("user.email", proposta.getEmail());

        //analisa a proposta para torná-la elegível ou não, em caso de restrições
        AnalisePropostaDTORequest analisePropostaDTORequest = new AnalisePropostaDTORequest(proposta.getDocumento(),
                proposta.getNome(), proposta.getId());
        try{
            AnalisaPropostaDTO analisaPropostaDto = consultaSwagger.analiseProposta(analisePropostaDTORequest);
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

    @GetMapping("/{id}")
    public ResponseEntity<?> acompanhamentoProposta(@PathVariable Long id) {
        Optional<Proposta> proposta = propostaRepository.findById(id);

        if (proposta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TratamentoDeErro("Proposta id", "ID de proposta inválido!"));
        }
        return ResponseEntity.ok(new PropostaDTO(proposta.get()));
    }
}
