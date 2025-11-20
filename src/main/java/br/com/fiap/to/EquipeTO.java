package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Representa uma Equipe ou Time de trabalho dentro da empresa.
 * Usado para agrupar funcionários e para a FK no cadastro de funcionários.
 */
public class EquipeTO {
    /**
     * ID único da Equipe (PK: ID_EQUIPE).
     */
    @NotNull(message = "O ID da equipe não pode ser nulo.")
    private int id;

    /**
     * Nome completo da equipe (NM_EQUIPE).
     */
    @NotBlank(message = "O nome da equipe é obrigatório.")
    @Size(max = 100, message = "O nome da equipe deve ter no máximo 100 caracteres.")
    private String nome;

    /**
     * Sigla curta da equipe (SG_EQUIPE).
     */
    @NotBlank(message = "A sigla da equipe é obrigatória.")
    @Size(min = 1, max = 10, message = "A sigla da equipe deve ter entre 1 e 10 caracteres.")
    private String sigla;

    /**
     * Setor/Departamento da equipe (DS_SETOR).
     */
    @NotBlank(message = "O setor da equipe é obrigatório.")
    @Size(max = 100, message = "O setor da equipe deve ter no máximo 100 caracteres.")
    private String setor;

    // Construtores
    public EquipeTO() {
    }

    public EquipeTO(int id, String nome, String sigla, String setor) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.setor = setor;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}