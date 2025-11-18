package br.com.fiap.bo;

import br.com.fiap.dao.CheckinHumorDAO;
import br.com.fiap.dao.FuncionarioDAO; // Necessário para a lógica de autorização
import br.com.fiap.to.CheckinHumorTO;
import br.com.fiap.to.FuncionarioTO; // Necessário para a lógica de autorização
import br.com.fiap.to.RelatorioHumorTO;
import br.com.fiap.exception.AcessoNegadoException; // Exceção para o acesso negado

import java.util.ArrayList;

/**
 * Gerencia a lógica de negócio do Questionário de Humor.
 * Aplica a regra de unicidade diária e de autorização de consulta.
 */
public class CheckinHumorBO {

    private final CheckinHumorDAO dao = new CheckinHumorDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO(); // Para checar o perfil
    private static final int ID_FUNCAO_RH = 5;

    /**
     * Salva um novo Check-in. Impede registros duplicados na mesma data.
     * @return O CheckinHumorTO salvo ou null se já houver registro na data.
     */
    public CheckinHumorTO save(CheckinHumorTO checkin) {

        // REGRA DE NEGÓCIO: VERIFICAR DUPLICIDADE DIÁRIA
        CheckinHumorTO checkinExistente = dao.findByFuncionarioAndDate(
                checkin.getFuncionarioId(),
                checkin.getDataCheckin()
        );

        if (checkinExistente != null) {
            return null; // Retorna null se houver duplicidade (tratado como 400 no Resource)
        }
        return dao.save(checkin);
    }

    /**
     * Retorna todo o histórico de Check-ins para auditoria.
     * Implementa a REGRA: Apenas RH (ID_FUNCAO = 5) pode acessar o histórico bruto.
     * @param solicitanteId ID do funcionário que está requisitando o histórico.
     * @return O histórico completo de Check-ins.
     * @throws AcessoNegadoException Se o solicitante não for do RH.
     */
    public ArrayList<CheckinHumorTO> findAll(int solicitanteId) throws AcessoNegadoException {

        // Validar Permissão do Solicitante
        FuncionarioTO solicitante = funcionarioDAO.findByCodigo(solicitanteId);

        if (solicitante == null || solicitante.getIdFuncao() != ID_FUNCAO_RH) {
            throw new AcessoNegadoException("Acesso negado. Apenas o RH pode consultar o histórico BRUTO de Check-ins.");
        }

        // Se a permissão for concedida, busca os dados
        return dao.findAll();
    }

    /**
     * Consulta os dados agregados (média de energia/humor por equipe) para o Dashboard.
     * @return Lista de RelatorioHumorTO.
     */
    public ArrayList<RelatorioHumorTO> consultarRelatorioHumor() {
        // O cálculo é delegado ao DAO (AVG e GROUP BY). O filtro de equipe/perfil é delegado ao Resource.
        return dao.findMediaHumorPorEquipe();
    }
}