package propostas.propostas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import propostas.propostas.dto.BloqueioDTORequest;
import propostas.propostas.dto.CartaoDTORequest;
import propostas.propostas.dto.InformaStatusCartaoDTO;

@FeignClient(name = "cartao", url = "${proposta.cartao.host}")
public interface CartaoFeign {


    @GetMapping("${proposta.cartoes}")
    CartaoDTORequest buscarCartao(@RequestParam(name = "idProposta") String idProposta);

    //essa variável de ambiente busca no properties
    @PostMapping("${cartao.bloqueio}")
    InformaStatusCartaoDTO notificaSistema(@PathVariable String id, @RequestBody BloqueioDTORequest bloqueioDTORequest);
}
