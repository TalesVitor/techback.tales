package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping
    public List<FavoritoDTO> listar() {
        return favoritoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                favoritoService.buscarPorId(id)
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<FavoritoDTO> listarPorUsuario(
            @PathVariable Long usuarioId) {

        return favoritoService.listarPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<FavoritoDTO> criar(
            @RequestBody FavoritoDTO dto) {

        return ResponseEntity.ok(
                favoritoService.salvar(dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        favoritoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}