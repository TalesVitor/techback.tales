package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.mapper.FavoritoMapper;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.FavoritoRepository;
import br.uniesp.si.techback.repository.FilmeRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;
    private final FavoritoMapper favoritoMapper;

    public List<FavoritoDTO> listar() {

        return favoritoRepository.findAll()
                .stream()
                .map(favoritoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FavoritoDTO buscarPorId(Long id) {

        Favorito favorito = favoritoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Favorito não encontrado"));

        return favoritoMapper.toDTO(favorito);
    }

    public List<FavoritoDTO> listarPorUsuario(Long usuarioId) {

        return favoritoRepository
                .findByUsuarioIdOrderByCriadoEmDesc(usuarioId)
                .stream()
                .map(favoritoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FavoritoDTO salvar(FavoritoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Filme filme = filmeRepository.findById(dto.getFilmeId())
                .orElseThrow(() ->
                        new RuntimeException("Filme não encontrado"));

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .filme(filme)
                .build();

        Favorito salvo = favoritoRepository.save(favorito);

        return favoritoMapper.toDTO(salvo);
    }

    public void excluir(Long id) {

        if (!favoritoRepository.existsById(id)) {
            throw new RuntimeException("Favorito não encontrado");
        }

        favoritoRepository.deleteById(id);
    }
}