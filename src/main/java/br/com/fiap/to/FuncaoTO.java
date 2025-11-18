package br.com.fiap.to;

/**
 * Representa uma Função ou Perfil de Cargo (Role) dentro da empresa.
 * Usado para definir o nível de acesso (Gestor, RH, Padrão) e para a FK no cadastro de funcionários.
 */
public class FuncaoTO {

    private int id; // Corresponde à coluna ID_FUNCAO
    private String nome; // Corresponde à coluna NM_FUNCAO

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