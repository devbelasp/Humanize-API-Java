package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Representa um Recurso de Bem-Estar (vídeo, artigo, meditação, etc.).
 * Usado para operações CRUD na biblioteca de conteúdo da plataforma Humanize.
 */
public class RecursoBemEstarTO {

    private int id; // PK: ID_RECURSO

    /**
     * Nome ou título do recurso. (NM_RECURSO)
     */
    @NotBlank(message = "O nome/título do recurso é obrigatório.")
    private String nome;

    /**
     * Tipo de conteúdo do recurso (Ex: Vídeo, Áudio, Artigo, E-book). (DS_TIPO)
     */
    @NotBlank(message = "O tipo de recurso (Artigo, Vídeo, Meditação) é obrigatório.")
    private String tipo;

    /**
     * Link ou URL de acesso ao recurso. (DS_LINK)
     */
    @NotBlank(message = "O link (URL) do recurso é obrigatório.")
    @Pattern(regexp = "^(http|https)://.*", message = "O link deve ser uma URL válida e começar com http:// ou https://.")
    private String link;

    // Construtores

    public RecursoBemEstarTO() {
    }

    public RecursoBemEstarTO(int id, String nome, String tipo, String link) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.link = link;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}