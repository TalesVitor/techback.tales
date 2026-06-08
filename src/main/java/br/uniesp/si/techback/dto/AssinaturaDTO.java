package br.uniesp.si.techback.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaDTO {

    private Long id;

    private Long usuarioId;

    private Long planoId;

    private String status;

    private LocalDateTime iniciadaEm;

    private LocalDateTime canceladaEm;
}