package br.com.fiap.to;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Representa o Questionário de Check-in de Humor (Monitoramento de Bem-Estar).
 * Mapeia as 10 perguntas centrais para a coleta anônima de dados sobre energia,
 * estresse, conexão social e hábitos físicos do funcionário.
 */
public class CheckinHumorTO {

    private int id; // PK: ID_HUMOR

    /**
     * ID do funcionário que está realizando o check-in. (FK: ID_FUNC)
     */
    @NotNull(message = "O ID do funcionário é obrigatório.")
    private int funcionarioId;

    /**
     * Data em que o check-in foi registrado (DT_CHECKIN). Essencial para a regra de unicidade diária.
     */
    @NotNull(message = "A data do check-in é obrigatória.")
    @PastOrPresent(message = "A data do check-in não pode ser futura.")
    private LocalDate dataCheckin;


    // CATEGORIA 1: ENERGIA E HUMOR

    /**
     * Q1: Nível de Energia na escala de 1 (Muito Baixo) a 5 (Muito Alto). (NR_ENERGIA)
     */
    @NotNull(message = "O nível de energia é obrigatório.")
    @Min(value = 1, message = "O nível de energia deve ser entre 1 e 5.")
    @Max(value = 5, message = "O nível de energia deve ser entre 1 e 5.")
    private int nivelEnergia;

    /**
     * Q2: Descritor Emocional. Sentimento predominante no dia (Ex: 'Focado', 'Ansioso'). (DS_SENTIMENTO)
     */
    @NotBlank(message = "O sentimento atual é obrigatório.")
    @Size(max = 50, message = "O sentimento deve ter no máximo 50 caracteres.")
    private String sentimento;


    // CATEGORIA 2: CARGA DE TRABALHO E ESTRESSE

    /**
     * Q3: Volume de Demandas. (TP_VOLUME)
     */
    @NotBlank(message = "O volume de demandas é obrigatório.")
    private String volumeDemandas;

    /**
     * Q4: Bloqueios e Ansiedade. Descrição opcional de problemas ou barreiras. (DS_BLOQUEIO)
     */
    @Size(max = 250, message = "A descrição do bloqueio deve ter no máximo 250 caracteres.")
    private String bloqueios;

    /**
     * Q5: Indicador de Desconexão (Sim/Não para Equilíbrio Vida-Trabalho). Se conseguiu se desconectar. (TP_EQUILIBRIO_VT)
     */
    @NotBlank(message = "O indicador de desconexão é obrigatório.")
    private String desconexao;


    // CATEGORIA 3: CONEXÃO SOCIAL

    /**
     * Q6: Nível de Pertencimento ou Conexão Social na escala de 1 a 5. (NR_CONEXAO)
     */
    @NotNull(message = "O nível de conexão social é obrigatório.")
    @Min(value = 1, message = "O nível de conexão deve ser entre 1 e 5.")
    @Max(value = 5, message = "O nível de conexão deve ser entre 1 e 5.")
    private int nivelConexao;

    /**
     * Q7: Qualidade das Interações Sociais no trabalho. (TP_INTERACAO)
     */
    @NotBlank(message = "A qualidade das interações é obrigatória.")
    private String qualidadeInteracao;


    // CATEGORIA 4: FÍSICO E AMBIENTE

    /**
     * Q8: Qualidade do Sono. (TP_SONO)
     */
    @NotBlank(message = "A avaliação do sono é obrigatória.")
    private String qualidadeSono;

    /**
     * Q9: Pausas e Respiração. Se conseguiu realizar pausas significativas. (TP_PAUSA)
     */
    @NotBlank(message = "O status de pausas é obrigatório.")
    private String statusPausas;


    // CATEGORIA 5: REFORÇO POSITIVO

    /**
     * Q10: O "Pequeno Ganho" ou conquista do dia. Campo motivacional opcional. (DS_PEQUENO_GANHO)
     */
    @Size(max = 250, message = "A descrição do ganho deve ter no máximo 250 caracteres.")
    private String pequenoGanho;

    // Construtores

    public CheckinHumorTO() {}

    public CheckinHumorTO(int id, int funcionarioId, LocalDate dataCheckin, int nivelEnergia, String sentimento, String volumeDemandas, String bloqueios, String desconexao, int nivelConexao, String qualidadeInteracao, String qualidadeSono, String statusPausas, String pequenoGanho) {
        this.id = id;
        this.funcionarioId = funcionarioId;
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

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
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