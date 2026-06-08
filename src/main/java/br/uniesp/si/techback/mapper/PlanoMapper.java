package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.model.Plano;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanoMapper {

    private final ModelMapper modelMapper;

    public PlanoDTO toDTO(Plano plano) {
        return modelMapper.map(plano, PlanoDTO.class);
    }

    public Plano toEntity(PlanoDTO planoDTO) {
        return modelMapper.map(planoDTO, Plano.class);
    }
}