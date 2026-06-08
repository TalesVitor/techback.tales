package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.validation.CPFValido;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    private String nomeCompleto;

    private LocalDate dataNascimento;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    @Size(max = 254)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8)
    private String senhaHash;

    @CPFValido
    private String cpf;

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
