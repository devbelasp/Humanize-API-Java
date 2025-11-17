package br.com.fiap.bo;

import br.com.fiap.dao.CheckinHumorDAO;
import br.com.fiap.to.CheckinHumorTO;
import br.com.fiap.to.RelatorioHumorTO;
import java.util.ArrayList;

/**
 * Gerencia a lógica de negócio do Questionário de Humor.
 * Aplica a regra de unicidade diária e prepara os dados para o Dashboard.
 */
public class CheckinHumorBO {

    private final CheckinHumorDAO dao = new CheckinHumorDAO();

    /**
     * Salva um novo Check-in. Impede registros duplicados na mesma data.
     */
    public CheckinHumorTO save(CheckinHumorTO checkin) {

        // REGRA DE NEGÓCIO: VERIFICAR DUPLICIDADE DIÁRIA
        CheckinHumorTO checkinExistente = dao.findByFuncionarioAndDate(
                checkin.getFuncionarioId(),
                checkin.getDataCheckin()
        );

        if (checkinExistente != null) {
            return null;
        }
        return dao.save(checkin);
    }

    /**
     * Retorna todo o histórico de Check-ins para auditoria ou consulta.
     */
    public ArrayList<CheckinHumorTO> findAll() {
        return dao.findAll();
    }

    /**
     * Consulta os dados agregados (média de energia/humor por equipe) para o Dashboard.
     */
    public ArrayList<RelatorioHumorTO> consultarRelatorioHumor() {
        // O cálculo é delegado ao DAO (AVG e GROUP BY)
        return dao.findMediaHumorPorEquipe();
    }
}