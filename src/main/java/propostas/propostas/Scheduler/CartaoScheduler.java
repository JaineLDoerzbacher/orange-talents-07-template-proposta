package propostas.propostas.Scheduler;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import propostas.propostas.dto.CartaoDTORequest;
import propostas.propostas.entities.Cartao;
import propostas.propostas.entities.Proposta;
import propostas.propostas.enuns.StatusProposta;
import propostas.propostas.feign.CartaoFeign;
import propostas.propostas.repositories.CartaoRepository;
import propostas.propostas.repositories.PropostaRepository;

import java.util.List;

@Component
public class CartaoScheduler {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoFeign cartaoFeign;

    @Scheduled(fixedDelay = 1000 * 60)
    public void associaCartaoProposta() {
        List<Proposta> propostas = propostaRepository.findByStatusDeCartaoElegivel(StatusProposta.ELEGIVEL);
        propostas.forEach(System.out::println);

        for (Proposta proposta : propostas) {
            try{
                CartaoDTORequest cartaoRequest = cartaoFeign.buscarCartao(String.valueOf(proposta.getId()));
                Cartao cartao = cartaoRequest.toModel(propostaRepository);

                cartaoRepository.save(cartao);

                proposta.setCartao(cartao);

                propostaRepository.save(proposta);
                System.out.println("Proposta de documento " + proposta.getDocumento()
                        + " e cartao "+ cartaoRequest.getId() +" criados com sucesso.");
            } catch (FeignException ex) {
                ex.printStackTrace();
            }
        }
    }
}
