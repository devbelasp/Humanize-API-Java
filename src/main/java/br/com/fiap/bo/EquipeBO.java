package br.com.fiap.bo;

import br.com.fiap.dao.EquipeDAO;
import br.com.fiap.to.EquipeTO;
import java.util.ArrayList;

/**
 * Gerencia a lógica de negócio para a entidade Equipe.
 * Atua como intermediário entre o Resource e o DAO para as consultas de referência.
 */
public class EquipeBO {

    private final EquipeDAO dao = new EquipeDAO();

    /**
     * Busca e retorna a lista de todas as Equipes cadastradas.
     * @return ArrayList de EquipeTO.
     */
    public ArrayList<EquipeTO> findAll() {
        return dao.findAll();
    }
}