package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinaturas")
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @GetMapping
    public List<AssinaturaDTO> listar() {
        return assinaturaService.listar();
    }

    @GetMapping("/status/{status}")
    public List<AssinaturaDTO> buscarPorStatus(
            @PathVariable String status) {

        return assinaturaService.buscarPorStatus(status);
    }

    @PostMapping
    public ResponseEntity<AssinaturaDTO> criar(
            @Valid @RequestBody AssinaturaDTO dto) {

        return ResponseEntity.ok(
                assinaturaService.salvar(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AssinaturaDTO dto) {

        return ResponseEntity.ok(
                assinaturaService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        assinaturaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}