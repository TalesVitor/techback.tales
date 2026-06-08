package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.mapper.AssinaturaMapper;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import br.uniesp.si.techback.repository.PlanoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoRepository planoRepository;
    private final AssinaturaMapper assinaturaMapper;

    public List<AssinaturaDTO> listar() {

        log.info("Listando todas as assinaturas");

        return assinaturaRepository.findAll()
                .stream()
                .map(assinaturaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AssinaturaDTO> buscarPorStatus(String status) {

        log.info("Buscando assinaturas por status={}", status);

        return assinaturaRepository.findByStatus(status)
                .stream()
                .map(assinaturaMapper::toDTO)
                .toList();
    }

    public AssinaturaDTO salvar(AssinaturaDTO dto) {

        log.info("Criando assinatura para usuarioId={} planoId={}",
                dto.getUsuarioId(), dto.getPlanoId());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado: {}", dto.getUsuarioId());
                    return new RuntimeException("Usuário não encontrado");
                });

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> {
                    log.error("Plano não encontrado: {}", dto.getPlanoId());
                    return new RuntimeException("Plano não encontrado");
                });

        Assinatura assinatura = Assinatura.builder()
                .usuario(usuario)
                .plano(plano)
                .status(dto.getStatus())
                .build();

        Assinatura salva = assinaturaRepository.save(assinatura);

        log.info("Assinatura criada com sucesso id={}", salva.getId());

        return assinaturaMapper.toDTO(salva);
    }

    public AssinaturaDTO atualizar(Long id, AssinaturaDTO dto) {

        log.info("Atualizando assinatura id={}", id);

        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Assinatura não encontrada id={}", id);
                    return new RuntimeException("Assinatura não encontrada");
                });

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado: {}", dto.getUsuarioId());
                    return new RuntimeException("Usuário não encontrado");
                });

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> {
                    log.error("Plano não encontrado: {}", dto.getPlanoId());
                    return new RuntimeException("Plano não encontrado");
                });

        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);
        assinatura.setStatus(dto.getStatus());

        if ("CANCELADA".equalsIgnoreCase(dto.getStatus())) {
            log.info("Assinatura id={} cancelada", id);
            assinatura.setCanceladaEm(LocalDateTime.now());
        }

        Assinatura atualizada = assinaturaRepository.save(assinatura);

        log.info("Assinatura atualizada com sucesso id={}", id);

        return assinaturaMapper.toDTO(atualizada);
    }

    public void excluir(Long id) {

        log.warn("Solicitação de exclusão de assinatura id={}", id);

        if (!assinaturaRepository.existsById(id)) {
            log.error("Tentativa de excluir assinatura inexistente id={}", id);
            throw new RuntimeException("Assinatura não encontrada");
        }

        assinaturaRepository.deleteById(id);

        log.info("Assinatura excluída com sucesso id={}", id);
    }

    public List<Object[]> contarAssinaturasAtivasPorPlano() {

        log.info("Gerando relatório de assinaturas ativas por plano");

        return assinaturaRepository.countAssinaturasAtivasPorPlano();
    }
}