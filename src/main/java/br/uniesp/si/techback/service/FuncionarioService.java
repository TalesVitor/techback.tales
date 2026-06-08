package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.ViaCepClient;
import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.exception.CustomBeanException;
import br.uniesp.si.techback.mapper.FuncionarioMapper;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final ViaCepClient viaCepClient;
    private final FuncionarioMapper funcionarioMapper;

    public List<FuncionarioDTO> listar() {
        return funcionarioRepository.findAll()
                .stream()
                .map(funcionarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FuncionarioDTO buscarPorId(Long id) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Funcionário não encontrado"));

        return funcionarioMapper.toDTO(funcionario);
    }

    public FuncionarioDTO incluir(FuncionarioDTO funcionarioDTO) {

        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);

        if (funcionario.getCep() != null && !funcionario.getCep().isBlank()) {

            String cepLimpo = funcionario.getCep().replaceAll("\\D", "");

            ViaCepResponseDTO endereco =
                    viaCepClient.buscarPorCep(cepLimpo);

            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new CustomBeanException(
                        "CEP invalido para consulta no ViaCEP");
            }

            funcionario.setCep(endereco.getCep());
            funcionario.setLogradouro(endereco.getLogradouro());
            funcionario.setBairro(endereco.getBairro());
            funcionario.setLocalidade(endereco.getLocalidade());
            funcionario.setUf(endereco.getUf());
        }

        Funcionario salvo =
                funcionarioRepository.save(funcionario);

        return funcionarioMapper.toDTO(salvo);
    }

    public FuncionarioDTO atualizar(Long id,
                                    FuncionarioDTO funcionarioDTO) {

        Funcionario existente = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Funcionário não encontrado"));

        existente.setNome(funcionarioDTO.getNome());
        existente.setCargo(funcionarioDTO.getCargo());
        existente.setCep(funcionarioDTO.getCep());

        FuncionarioDTO atualizado = incluir(
                funcionarioMapper.toDTO(existente)
        );

        return atualizado;
    }

    public void excluir(Long id) {

        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        funcionarioRepository.deleteById(id);
    }
}