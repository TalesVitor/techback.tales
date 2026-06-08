package br.uniesp.si.techback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {

    private Long id;

    private Long usuarioId;

    private Long filmeId;

    private LocalDateTime criadoEm;
}