package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Representa uma Função ou Perfil de Cargo (Role) dentro da empresa.
 * Usado para definir o nível de acesso (Gestor, RH, Padrão) e para a FK no
 * cadastro de funcionários.
 */
public class FuncaoTO {
    /**
     * ID único da Função (PK: ID_FUNCAO).
     */
    @NotNull(message = "O ID da função não pode ser nulo.")
    private int id;

    /**
     * Nome do Perfil/Cargo (NM_FUNCAO).
     */
    @NotBlank(message = "O nome da função é obrigatório.")
    @Size(max = 50, message = "O nome da função deve ter no máximo 50 caracteres.")
    private String nome;

    // Construtores
    public FuncaoTO() {
    }

    public FuncaoTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
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
}