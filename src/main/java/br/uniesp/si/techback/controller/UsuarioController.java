package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<UsuarioDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                usuarioService.listarPaginado(page, size)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                usuarioService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(
            @Valid @RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(
                usuarioService.salvar(usuarioDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(
                usuarioService.atualizar(id, usuarioDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        usuarioService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}