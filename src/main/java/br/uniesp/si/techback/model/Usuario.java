package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome_completo", nullable = false, length = 150)
    private String nomeCompleto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(unique = true, length = 11)
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

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}