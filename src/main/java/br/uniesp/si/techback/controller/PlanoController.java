package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService planoService;

    @GetMapping
    public List<PlanoDTO> listar() {
        return planoService.listar();
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PlanoDTO> buscarPorCodigo(
            @PathVariable String codigo) {

        return ResponseEntity.ok(
                planoService.buscarPorCodigo(codigo)
        );
    }

    @PostMapping
    public ResponseEntity<PlanoDTO> criar(
            @Valid @RequestBody PlanoDTO planoDTO) {

        return ResponseEntity.ok(
                planoService.salvar(planoDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanoDTO planoDTO) {

        return ResponseEntity.ok(
                planoService.atualizar(id, planoDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        planoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}