package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.model.MetodoPagamento;
import org.springframework.stereotype.Component;

@Component
public class MetodoPagamentoMapper {

    public MetodoPagamentoDTO toDTO(
            MetodoPagamento metodoPagamento) {

        return MetodoPagamentoDTO.builder()
                .id(metodoPagamento.getId())
                .usuarioId(
                        metodoPagamento.getUsuario().getId()
                )
                .bandeira(metodoPagamento.getBandeira())
                .ultimos4(metodoPagamento.getUltimos4())
                .mesExp(metodoPagamento.getMesExp())
                .anoExp(metodoPagamento.getAnoExp())
                .nomePortador(
                        metodoPagamento.getNomePortador()
                )
                .tokenGateway(
                        metodoPagamento.getTokenGateway()
                )
                .build();
    }
}