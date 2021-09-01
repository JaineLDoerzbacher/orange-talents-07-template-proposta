package propostas.propostas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import propostas.propostas.dto.CartaoDTORequest;

@FeignClient(name = "cartao", url = "${proposta.cartao.host}")
public interface CartaoFeign {


    @GetMapping("${proposta.cartoes}")
    CartaoDTORequest buscarCartao(@RequestParam(name = "idProposta") String idProposta);


}
