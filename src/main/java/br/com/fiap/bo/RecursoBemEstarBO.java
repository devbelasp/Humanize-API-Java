package br.com.fiap.bo;

import br.com.fiap.dao.RecursoBemEstarDAO;
import br.com.fiap.to.RecursoBemEstarTO;
import java.util.ArrayList;

/**
 * Gerencia a lógica de negócio e orquestra as operações CRUD para os Recursos de Bem-Estar.
 * Esta classe atua como intermediário entre o Resource e o DAO.
 */
public class RecursoBemEstarBO {

    private final RecursoBemEstarDAO dao = new RecursoBemEstarDAO();

    /**
     * Salva um novo Recurso de Bem-Estar no banco.
     */
    public RecursoBemEstarTO save(RecursoBemEstarTO recurso) {
        // Nenhuma regra de negócio complexa aqui, apenas chama o DAO.
        return dao.save(recurso);
    }

    /**
     * Busca todos os recursos cadastrados.
     */
    public ArrayList<RecursoBemEstarTO> findAll() {
        return dao.findAll();
    }

    /**
     * Busca um recurso pelo seu ID.
     */
    public RecursoBemEstarTO findById(int id) {
        return dao.findById(id);
    }

    /**
     * Atualiza um recurso existente no banco.
     */
    public RecursoBemEstarTO update(RecursoBemEstarTO recurso) {
        return dao.update(recurso);
    }

    /**
     * Exclui um recurso pelo seu ID.
     */
    public boolean delete(int id) {
        return dao.delete(id);
    }
}