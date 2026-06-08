package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagamentoDTO {

    private Long id;

    private Long usuarioId;

    @NotBlank(message = "Bandeira é obrigatória")
    private String bandeira;

    private String ultimos4;

    private Integer mesExp;

    private Integer anoExp;

    private String nomePortador;

    private String tokenGateway;
}