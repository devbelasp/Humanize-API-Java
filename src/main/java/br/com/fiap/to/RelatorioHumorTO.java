package br.com.fiap.to;

/**
 * Representa os dados calculados para o Dashboard.
 * Contém a média de humor por equipe e o total de check-ins usados no cálculo.
 */
public class RelatorioHumorTO {

    private int equipeId;
    private String nomeEquipe;
    private double mediaNivelHumor;
    private int totalCheckins; // Total de registros que compuseram a média

    // Construtores

    public RelatorioHumorTO() {
    }

    public RelatorioHumorTO(int equipeId, String nomeEquipe, double mediaNivelHumor, int totalCheckins) {
        this.equipeId = equipeId;
        this.nomeEquipe = nomeEquipe;
        this.mediaNivelHumor = mediaNivelHumor;
        this.totalCheckins = totalCheckins;
    }

    // Getters e Setters

    public int getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(int equipeId) {
        this.equipeId = equipeId;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public double getMediaNivelHumor() {
        return mediaNivelHumor;
    }

    public void setMediaNivelHumor(double mediaNivelHumor) {
        this.mediaNivelHumor = mediaNivelHumor;
    }

    public int getTotalCheckins() {
        return totalCheckins;
    }

    public void setTotalCheckins(int totalCheckins) {
        this.totalCheckins = totalCheckins;
    }
}