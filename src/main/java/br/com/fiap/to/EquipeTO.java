package br.com.fiap.to;

public class EquipeTO {
    private int id;
    private String nome;
    private String sigla; // NOVA COLUNA
    private String setor;

    public EquipeTO() {
    }

    public EquipeTO(int id, String nome, String sigla, String setor) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.setor = setor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // Novos Getters e Setters
    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}