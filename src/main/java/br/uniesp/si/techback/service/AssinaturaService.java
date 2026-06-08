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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoRepository planoRepository;
    private final AssinaturaMapper assinaturaMapper;

    public List<AssinaturaDTO> listar() {

        return assinaturaRepository.findAll()
                .stream()
                .map(assinaturaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AssinaturaDTO> buscarPorStatus(
            String status) {

        return assinaturaRepository.findByStatus(status)
                .stream()
                .map(assinaturaMapper::toDTO)
                .toList();
    }

    public AssinaturaDTO salvar(AssinaturaDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() ->
                        new RuntimeException("Plano não encontrado"));

        Assinatura assinatura = Assinatura.builder()
                .usuario(usuario)
                .plano(plano)
                .status(dto.getStatus())
                .build();

        Assinatura salva = assinaturaRepository.save(assinatura);

        return assinaturaMapper.toDTO(salva);
    }

    public AssinaturaDTO atualizar(Long id,
                                   AssinaturaDTO dto) {

        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Assinatura não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() ->
                        new RuntimeException("Plano não encontrado"));

        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);
        assinatura.setStatus(dto.getStatus());

        if ("CANCELADA".equalsIgnoreCase(dto.getStatus())) {
            assinatura.setCanceladaEm(LocalDateTime.now());
        }

        Assinatura atualizada =
                assinaturaRepository.save(assinatura);

        return assinaturaMapper.toDTO(atualizada);
    }

    public void excluir(Long id) {

        if (!assinaturaRepository.existsById(id)) {
            throw new RuntimeException("Assinatura não encontrada");
        }

        assinaturaRepository.deleteById(id);
    }
}