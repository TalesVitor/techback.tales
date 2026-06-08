package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.model.Funcionario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuncionarioMapper {

    private final ModelMapper modelMapper;

    public FuncionarioDTO toDTO(Funcionario funcionario) {
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    public Funcionario toEntity(FuncionarioDTO funcionarioDTO) {
        return modelMapper.map(funcionarioDTO, Funcionario.class);
    }
}