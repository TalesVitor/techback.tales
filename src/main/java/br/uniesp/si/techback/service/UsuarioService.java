package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.ViaCepClient;
import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final ViaCepClient viaCepClient;

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        if (usuario.getCep() != null && !usuario.getCep().isBlank()) {

            String cepLimpo = usuario.getCep().replaceAll("\\D", "");

            ViaCepResponseDTO endereco =
                    viaCepClient.buscarPorCep(cepLimpo);

            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new RuntimeException(
                        "CEP inválido para consulta no ViaCEP");
            }

            usuario.setCep(endereco.getCep());
            usuario.setLogradouro(endereco.getLogradouro());
            usuario.setBairro(endereco.getBairro());
            usuario.setLocalidade(endereco.getLocalidade());
            usuario.setUf(endereco.getUf());
        }

        Usuario salvo = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(salvo);
    }

    public UsuarioDTO atualizar(Long id,
                                UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        usuario.setNomeCompleto(usuarioDTO.getNomeCompleto());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenhaHash(usuarioDTO.getSenhaHash());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setCep(usuarioDTO.getCep());

        if (usuario.getCep() != null && !usuario.getCep().isBlank()) {

            String cepLimpo = usuario.getCep().replaceAll("\\D", "");

            ViaCepResponseDTO endereco =
                    viaCepClient.buscarPorCep(cepLimpo);

            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new RuntimeException(
                        "CEP inválido para consulta no ViaCEP");
            }

            usuario.setCep(endereco.getCep());
            usuario.setLogradouro(endereco.getLogradouro());
            usuario.setBairro(endereco.getBairro());
            usuario.setLocalidade(endereco.getLocalidade());
            usuario.setUf(endereco.getUf());
        }

        Usuario atualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(atualizado);
    }

    public void excluir(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);


    }

    public Page<UsuarioDTO> listarPaginado(
            int page,
            int size) {

        return usuarioRepository
                .findAll(PageRequest.of(page, size))
                .map(usuarioMapper::toDTO);
    }
}