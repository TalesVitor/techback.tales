package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Cargo é obrigatório")
    private String cargo;

    @Pattern(
            regexp = "\\d{5}-?\\d{3}",
            message = "CEP inválido"
    )
    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;
}