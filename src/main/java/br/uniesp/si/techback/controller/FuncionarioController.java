package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    public List<FuncionarioDTO> listar() {
        return funcionarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                funcionarioService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> incluir(
            @Valid @RequestBody FuncionarioDTO funcionarioDTO) {

        return ResponseEntity.ok(
                funcionarioService.incluir(funcionarioDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioDTO funcionarioDTO) {

        return ResponseEntity.ok(
                funcionarioService.atualizar(id, funcionarioDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        funcionarioService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}