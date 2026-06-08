package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.model.Favorito;
import org.springframework.stereotype.Component;

@Component
public class FavoritoMapper {

    public FavoritoDTO toDTO(Favorito favorito) {

        return FavoritoDTO.builder()
                .id(favorito.getId())
                .usuarioId(favorito.getUsuario().getId())
                .filmeId(favorito.getFilme().getId())
                .criadoEm(favorito.getCriadoEm())
                .build();
    }
}