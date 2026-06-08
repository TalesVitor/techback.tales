package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planos")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Código é obrigatório")
    @Column(unique = true, nullable = false)
    private String codigo;

    private Integer limiteDiario;

    private Integer streamsSimultaneos;
}
