package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FilmeDTO;
import br.uniesp.si.techback.mapper.FilmeMapper;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public List<Filme> listarOrdenado() {
        return filmeRepository.listarFilmesOrdenados();
    }

    public List<FilmeDTO> listar() {
        log.info("Buscando todos os filmes cadastrados");
        try {
            List<Filme> filmes = filmeRepository.findAll();
            List<FilmeDTO> filmesDTO = filmes.stream()
                    .map(filmeMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de filmes encontrados: {}", filmesDTO.size());
            return filmesDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar filmes: {}", e.getMessage(), e);
            throw e;
        }
    }

    public FilmeDTO buscarPorId(Long id) {
        log.info("Buscando filme pelo ID: {}", id);
        Filme filme = filmeRepository.findById(id)
                .map(filmeEncontrado -> {
                    log.debug("Filme encontrado: ID={}, Título={}", filmeEncontrado.getId(), filmeEncontrado.getTitulo());
                    return filmeEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Filme não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });

        return filmeMapper.toDTO(filme);
    }

    @Transactional
    public FilmeDTO atualizar(Long id, FilmeDTO filmeDTO) {
        log.info("Atualizando filme ID: {}", id);

        Filme filmeAtualizado = filmeRepository.findById(id)
                .map(filmeExistente -> {
                    filmeDTO.setId(id);
                    Filme filmeParaAtualizar = filmeMapper.toEntity(filmeDTO);
                    return filmeRepository.save(filmeParaAtualizar);
                })
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        return filmeMapper.toDTO(filmeAtualizado);
    }

    @Transactional
    public FilmeDTO salvar(FilmeDTO filmeDTO) {
        log.info("Salvando novo filme: {}", filmeDTO.getTitulo());

        Filme filme = filmeMapper.toEntity(filmeDTO);
        Filme filmeSalvo = filmeRepository.save(filme);

        return filmeMapper.toDTO(filmeSalvo);
    }

    @Transactional
    public void excluir(Long id) {

        if (!filmeRepository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado");
        }

        filmeRepository.deleteById(id);
    }

    // MÉTODO DE PAGINAÇÃO
    public Page<FilmeDTO> listarPaginado(int page, int size) {

        return filmeRepository
                .findAll(PageRequest.of(page, size))
                .map(filmeMapper::toDTO);
    }

    public FilmeDTO buscarPorGenero(String genero, String titulo) {

        Filme filme = filmeRepository.buscarPorGenero(genero, titulo);

        if (filme == null) {
            throw new RuntimeException("Filme não encontrado");
        }

        return filmeMapper.toDTO(filme);
    }
}