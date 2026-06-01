package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.ViaCepClient;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.exception.CustomBeanException;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final ViaCepClient viaCepClient;

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Funcionário não encontrado"));
    }

    public Funcionario atualizar(Long id, Funcionario funcionario) {

        Funcionario existente = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Funcionário não encontrado"));

        existente.setNome(funcionario.getNome());
        existente.setCargo(funcionario.getCargo());
        existente.setCep(funcionario.getCep());

        return incluir(existente);
    }

    public void excluir(Long id) {

        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        funcionarioRepository.deleteById(id);
    }

    public Funcionario incluir(Funcionario funcionario) {
        if (funcionario.getCep() != null && !funcionario.getCep().isBlank()) {
            String cepLimpo = funcionario.getCep().replaceAll("\\D", "");
            ViaCepResponseDTO endereco = viaCepClient.buscarPorCep(cepLimpo);

            // Exemplo simples para a turma: quando a API retorna erro, lancamos a excecao customizada.
            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new CustomBeanException("CEP invalido para consulta no ViaCEP");
            }

            funcionario.setCep(endereco.getCep());
            funcionario.setLogradouro(endereco.getLogradouro());
            funcionario.setBairro(endereco.getBairro());
            funcionario.setLocalidade(endereco.getLocalidade());
            funcionario.setUf(endereco.getUf());
        }

        return funcionarioRepository.save(funcionario);
    }
}

