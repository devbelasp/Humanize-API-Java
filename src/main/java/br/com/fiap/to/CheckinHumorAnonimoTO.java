package br.com.fiap.to;

import java.time.LocalDate;

/**
 * Representa uma entrada de Check-in de Humor (dados brutos).
 * Este objeto é usado especificamente para retornar o histórico de dados
 * para o Dashboard de Gestão/RH, garantindo que o ID do funcionário
 * (funcionarioId) não seja exposto, conforme a regra de anonimização.
 */
public class CheckinHumorAnonimoTO {

    private int id; // PK: ID_HUMOR
    private LocalDate dataCheckin;

    // CATEGORIA 1: ENERGIA E HUMOR
    private int nivelEnergia;       // Q1: Nível de Energia (1 a 5)
    private String sentimento;       // Q2: Sentimento predominante

    // CATEGORIA 2: CARGA DE TRABALHO E ESTRESSE
    private String volumeDemandas;  // Q3: Volume de Demandas
    private String bloqueios;       // Q4: Descrição de bloqueios (opcional)
    private String desconexao;      // Q5: Indicador de Desconexão (Equilíbrio VT)

    // CATEGORIA 3: CONEXÃO SOCIAL
    private int nivelConexao;       // Q6: Nível de Conexão Social (1 a 5)
    private String qualidadeInteracao; // Q7: Qualidade das Interações

    // CATEGORIA 4: FÍSICO E AMBIENTE
    private String qualidadeSono;     // Q8: Qualidade do Sono
    private String statusPausas;      // Q9: Status de Pausas

    // CATEGORIA 5: REFORÇO POSITIVO
    private String pequenoGanho;      // Q10: Pequeno Ganho do dia (opcional)

    // Construtores

    public CheckinHumorAnonimoTO() {
    }

    public CheckinHumorAnonimoTO(int id, LocalDate dataCheckin, int nivelEnergia, String sentimento, String volumeDemandas, String bloqueios, String desconexao, int nivelConexao, String qualidadeInteracao, String qualidadeSono, String statusPausas, String pequenoGanho) {
        this.id = id;
        this.dataCheckin = dataCheckin;
        this.nivelEnergia = nivelEnergia;
        this.sentimento = sentimento;
        this.volumeDemandas = volumeDemandas;
        this.bloqueios = bloqueios;
        this.desconexao = desconexao;
        this.nivelConexao = nivelConexao;
        this.qualidadeInteracao = qualidadeInteracao;
        this.qualidadeSono = qualidadeSono;
        this.statusPausas = statusPausas;
        this.pequenoGanho = pequenoGanho;
    }

    // Getters e Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(LocalDate dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public int getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(int nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public String getSentimento() {
        return sentimento;
    }

    public void setSentimento(String sentimento) {
        this.sentimento = sentimento;
    }

    public String getVolumeDemandas() {
        return volumeDemandas;
    }

    public void setVolumeDemandas(String volumeDemandas) {
        this.volumeDemandas = volumeDemandas;
    }

    public String getBloqueios() {
        return bloqueios;
    }

    public void setBloqueios(String bloqueios) {
        this.bloqueios = bloqueios;
    }

    public String getDesconexao() {
        return desconexao;
    }

    public void setDesconexao(String desconexao) {
        this.desconexao = desconexao;
    }

    public int getNivelConexao() {
        return nivelConexao;
    }

    public void setNivelConexao(int nivelConexao) {
        this.nivelConexao = nivelConexao;
    }

    public String getQualidadeInteracao() {
        return qualidadeInteracao;
    }

    public void setQualidadeInteracao(String qualidadeInteracao) {
        this.qualidadeInteracao = qualidadeInteracao;
    }

    public String getQualidadeSono() {
        return qualidadeSono;
    }

    public void setQualidadeSono(String qualidadeSono) {
        this.qualidadeSono = qualidadeSono;
    }

    public String getStatusPausas() {
        return statusPausas;
    }

    public void setStatusPausas(String statusPausas) {
        this.statusPausas = statusPausas;
    }

    public String getPequenoGanho() {
        return pequenoGanho;
    }

    public void setPequenoGanho(String pequenoGanho) {
        this.pequenoGanho = pequenoGanho;
    }
}