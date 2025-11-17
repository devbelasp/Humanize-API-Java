package br.com.fiap.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * Representa um Funcionário e suas credenciais no sistema Humanize.
 * Usado para operações CRUD (Cadastro, Atualização) e Fluxo de Login.
 */
public class FuncionarioTO {

    private int id;

    // Campo obrigatório para Cadastro
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    // Campo obrigatório e único para Login e Cadastro
    @NotBlank(message = "O email para login é obrigatório.")
    @Email(message = "Formato de email inválido.")
    private String email;

    // Campo obrigatório para Login e Cadastro
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    // Campo obrigatório para Cadastro (DT_CONTRATACAO)
    @NotNull(message = "A data de contratação é obrigatória.")
    @PastOrPresent(message = "A data de contratação não pode ser futura.")
    private LocalDate dataContratacao;

    // FK: ID da Equipe - Obrigatório para o cadastro
    @NotNull(message = "O ID da equipe é obrigatório.")
    private int equipeId;

    // FK: ID da Função (ROLE) - Para distinguir Funcionário Normal de Gestor/RH
    @NotNull(message = "O ID da função (perfil) é obrigatório.")
    private int idFuncao;

    // Construtores

    public FuncionarioTO() {
    }

    public FuncionarioTO(int id, String nome, String email, String senha, LocalDate dataContratacao, int equipeId, int idFuncao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataContratacao = dataContratacao;
        this.equipeId = equipeId;
        this.idFuncao = idFuncao;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public int getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(int equipeId) {
        this.equipeId = equipeId;
    }

    public int getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(int idFuncao) {
        this.idFuncao = idFuncao;
    }
}