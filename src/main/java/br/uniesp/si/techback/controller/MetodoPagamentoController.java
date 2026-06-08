package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodos-pagamento")
@RequiredArgsConstructor
public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    @GetMapping
    public List<MetodoPagamentoDTO> listar() {
        return metodoPagamentoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagamentoDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                metodoPagamentoService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<MetodoPagamentoDTO> criar(
            @Valid @RequestBody MetodoPagamentoDTO dto) {

        return ResponseEntity.ok(
                metodoPagamentoService.salvar(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagamentoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MetodoPagamentoDTO dto) {

        return ResponseEntity.ok(
                metodoPagamentoService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        metodoPagamentoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}