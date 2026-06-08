package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metodos_pagamento")
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "Bandeira é obrigatória")
    private String bandeira;

    @Column(length = 4)
    private String ultimos4;

    private Integer mesExp;

    private Integer anoExp;

    private String nomePortador;

    private String tokenGateway;
}