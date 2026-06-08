package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.mapper.PlanoMapper;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;

    public List<PlanoDTO> listar() {
        return planoRepository.findAll()
                .stream()
                .map(planoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PlanoDTO buscarPorCodigo(String codigo) {

        Plano plano = planoRepository
                .findByCodigo(codigo)
                .orElseThrow(() ->
                        new RuntimeException("Plano não encontrado"));

        return planoMapper.toDTO(plano);
    }

    public PlanoDTO salvar(PlanoDTO planoDTO) {

        Plano plano = planoMapper.toEntity(planoDTO);

        Plano salvo = planoRepository.save(plano);

        return planoMapper.toDTO(salvo);
    }

    public PlanoDTO atualizar(Long id,
                              PlanoDTO planoDTO) {

        Plano plano = planoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Plano não encontrado"));

        plano.setCodigo(planoDTO.getCodigo());
        plano.setLimiteDiario(planoDTO.getLimiteDiario());
        plano.setStreamsSimultaneos(
                planoDTO.getStreamsSimultaneos()
        );

        Plano atualizado =
                planoRepository.save(plano);

        return planoMapper.toDTO(atualizado);
    }

    public void excluir(Long id) {

        if (!planoRepository.existsById(id)) {
            throw new RuntimeException("Plano não encontrado");
        }

        planoRepository.deleteById(id);
    }
}