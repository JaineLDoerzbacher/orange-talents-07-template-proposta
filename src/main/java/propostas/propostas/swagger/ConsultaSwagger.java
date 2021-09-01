package propostas.propostas.swagger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import propostas.propostas.dto.AnalisaPropostaDTO;
import propostas.propostas.dto.AnalisePropostaDTORequest;

@FeignClient(url = "${proposta.analise-financeira.host}", name = "consultaSwagger")
public interface ConsultaSwagger {

    @RequestMapping(value = "${proposta.analise-financeira.solicitacao}", method = RequestMethod.POST, consumes = "application/json")
    AnalisaPropostaDTO analiseProposta(AnalisePropostaDTORequest analisePropostaRequest);
}
