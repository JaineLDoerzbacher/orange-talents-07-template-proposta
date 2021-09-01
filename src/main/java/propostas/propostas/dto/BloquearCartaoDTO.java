package propostas.propostas.dto;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import propostas.propostas.entities.Cartao;
import propostas.propostas.feign.CartaoFeign;

import javax.servlet.http.HttpServletRequest;

public class BloquearCartaoDTO {

    @Autowired
    CartaoFeign cartaoFeign;

    public void bloquear(Cartao cartao, HttpServletRequest request) throws FeignException {
        cartaoFeign.notificaSistema(cartao.getNumeroCartao(), new BloqueioDTORequest("proposta"));
    }
}
