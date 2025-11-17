package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

/**
 * Representa os dados mínimos necessários para autenticar um usuário (Login).
 * Inclui validações para garantir que o email e a senha foram fornecidos.
 */
public class LoginTO {

    // Adicionado @Email para uma validação de formato de e-mail mais robusta
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O formato do e-mail é inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    // Construtores

    public LoginTO() {
    }

    public LoginTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters

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
}