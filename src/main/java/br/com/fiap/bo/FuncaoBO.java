package br.com.fiap.bo;

import br.com.fiap.dao.FuncaoDAO;
import br.com.fiap.to.FuncaoTO;
import java.util.ArrayList;

/**
 * Gerencia a lógica de negócio para a entidade Função.
 * Atua como intermediário entre o Resource e o DAO para as consultas de referência (findAll).
 */
public class FuncaoBO {

    private final FuncaoDAO dao = new FuncaoDAO();

    /**
     * Busca e retorna a lista de todas as Funções cadastradas.
     * @return ArrayList de FuncaoTO.
     */
    public ArrayList<FuncaoTO> findAll() {
        return dao.findAll();
    }
}