package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanoDTO {

    private Long id;

    @NotBlank(message = "Código é obrigatório")
    @Pattern(
            regexp = "BASICO|PADRAO|PREMIUM",
            message = "Código deve ser BASICO, PADRAO ou PREMIUM"
    )
    private String codigo;

    private Integer limiteDiario;

    private Integer streamsSimultaneos;
}