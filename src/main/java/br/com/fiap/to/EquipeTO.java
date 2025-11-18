package br.com.fiap.to;

/**
 * Representa uma Equipe (Squad, Departamento, etc.).
 * Usado primariamente para retornar as opções de FK na tela de cadastro de funcionários.
 */
public class EquipeTO {

    private int id; // Corresponde à coluna ID_EQUIPE
    private String nome; // Corresponde à coluna NM_EQUIPE
    private String setor; // Corresponde à coluna DS_SETOR

    // Construtores

    public EquipeTO() {
    }

    public EquipeTO(int id, String nome, String setor) {
        this.id = id;
        this.nome = nome;
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

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}