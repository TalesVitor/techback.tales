package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.mapper.MetodoPagamentoMapper;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MetodoPagamentoMapper metodoPagamentoMapper;

    public List<MetodoPagamentoDTO> listar() {

        return metodoPagamentoRepository.findAll()
                .stream()
                .map(metodoPagamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MetodoPagamentoDTO buscarPorId(Long id) {

        MetodoPagamento metodo = metodoPagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Método de pagamento não encontrado"));

        return metodoPagamentoMapper.toDTO(metodo);
    }

    public MetodoPagamentoDTO salvar(
            MetodoPagamentoDTO dto) {

        Usuario usuario = usuarioRepository
                .findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"));

        MetodoPagamento metodo =
                MetodoPagamento.builder()
                        .usuario(usuario)
                        .bandeira(dto.getBandeira())
                        .ultimos4(dto.getUltimos4())
                        .mesExp(dto.getMesExp())
                        .anoExp(dto.getAnoExp())
                        .nomePortador(dto.getNomePortador())
                        .tokenGateway(dto.getTokenGateway())
                        .build();

        MetodoPagamento salvo =
                metodoPagamentoRepository.save(metodo);

        return metodoPagamentoMapper.toDTO(salvo);
    }

    public MetodoPagamentoDTO atualizar(
            Long id,
            MetodoPagamentoDTO dto) {

        MetodoPagamento metodo =
                metodoPagamentoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Método de pagamento não encontrado"));

        Usuario usuario =
                usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuário não encontrado"));

        metodo.setUsuario(usuario);
        metodo.setBandeira(dto.getBandeira());
        metodo.setUltimos4(dto.getUltimos4());
        metodo.setMesExp(dto.getMesExp());
        metodo.setAnoExp(dto.getAnoExp());
        metodo.setNomePortador(dto.getNomePortador());
        metodo.setTokenGateway(dto.getTokenGateway());

        MetodoPagamento atualizado =
                metodoPagamentoRepository.save(metodo);

        return metodoPagamentoMapper.toDTO(atualizado);
    }

    public void excluir(Long id) {

        if (!metodoPagamentoRepository.existsById(id)) {
            throw new RuntimeException(
                    "Método de pagamento não encontrado");
        }

        metodoPagamentoRepository.deleteById(id);
    }
}