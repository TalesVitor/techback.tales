package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.model.Assinatura;
import org.springframework.stereotype.Component;

@Component
public class AssinaturaMapper {

    public AssinaturaDTO toDTO(Assinatura assinatura) {

        return AssinaturaDTO.builder()
                .id(assinatura.getId())
                .usuarioId(assinatura.getUsuario().getId())
                .planoId(assinatura.getPlano().getId())
                .status(assinatura.getStatus())
                .iniciadaEm(assinatura.getIniciadaEm())
                .canceladaEm(assinatura.getCanceladaEm())
                .build();
    }
}