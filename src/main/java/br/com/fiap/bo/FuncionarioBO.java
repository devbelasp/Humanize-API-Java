package br.com.fiap.bo;

import br.com.fiap.dao.CheckinHumorDAO;
import br.com.fiap.dao.FuncionarioDAO;
import br.com.fiap.dao.FuncionarioRecursoDAO;
import br.com.fiap.to.FuncionarioTO;
import br.com.fiap.to.RecursoBemEstarTO;
import br.com.fiap.exception.AcessoNegadoException;

import java.util.ArrayList;

/**
 * Camada de Lógica de Negócios (Business Object).
 * Centraliza as regras de negócio, de autorização e orquestra o acesso aos dados.
 */
public class FuncionarioBO {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final FuncionarioRecursoDAO funcRecursoDAO = new FuncionarioRecursoDAO();
    private final CheckinHumorDAO checkinDAO = new CheckinHumorDAO();

    private static final int ID_FUNCAO_RH = 5;

    /**
     * Implementa a REGRA: Apenas usuários com ID_FUNCAO = 5 (RH) podem cadastrar novos funcionários.
     */
    public FuncionarioTO cadastrarNovoFuncionario(FuncionarioTO novoFuncionario, int solicitanteId)
            throws AcessoNegadoException, RuntimeException {

        // Valida Permissão do Solicitante
        FuncionarioTO solicitante = funcionarioDAO.findByCodigo(solicitanteId);

        if (solicitante == null || solicitante.getIdFuncao() != ID_FUNCAO_RH) {
            throw new AcessoNegadoException("Acesso negado. Apenas usuários do RH podem realizar o cadastro de novos funcionários.");
        }

        // Aplica Regras de Negócio de Dados (Unicidade do E-mail)
        if (funcionarioDAO.findByEmail(novoFuncionario.getEmail()) != null) {
            throw new RuntimeException("O e-mail informado já está cadastrado.");
        }

        FuncionarioTO resultado = funcionarioDAO.save(novoFuncionario);

        if (resultado == null) {
            throw new RuntimeException("Erro ao persistir o novo funcionário no banco de dados.");
        }

        return resultado;
    }

    /**
     * Realiza o login do funcionário.
     */
    public FuncionarioTO login(String email, String senha) {
        return funcionarioDAO.buscarPorLogin(email, senha);
    }

    public ArrayList<FuncionarioTO> findAll() {
        return funcionarioDAO.findAll();
    }

    public FuncionarioTO findByCodigo(int id) {
        return funcionarioDAO.findByCodigo(id);
    }

    public FuncionarioTO findByEmail(String email) {
        return funcionarioDAO.findByEmail(email);
    }

    public FuncionarioTO update(FuncionarioTO funcionario) {
        return funcionarioDAO.update(funcionario);
    }

    /**
     * Exclusão em Cascata Manual (BO Orchestration).
     * Remove dependências (Check-ins e Favoritos) antes de remover o usuário.
     */
    public boolean delete(int id) {
        checkinDAO.deleteByFuncionarioId(id);

        funcionarioDAO.deleteRecursosAssociados(id);

        return funcionarioDAO.delete(id);
    }

    /**
     * Adiciona um recurso à lista de favoritos do funcionário.
     */
    public boolean associarRecurso(int idFunc, int idRecurso) {
        return funcRecursoDAO.associarRecurso(idFunc, idRecurso);
    }

    /**
     * Remove um recurso da lista de favoritos do funcionário.
     */
    public boolean desassociarRecurso(int idFunc, int idRecurso) {
        return funcRecursoDAO.desassociarRecurso(idFunc, idRecurso);
    }

    /**
     * Lista todos os recursos favoritos de um funcionário.
     */
    public ArrayList<RecursoBemEstarTO> listarRecursosFavoritos(int idFunc) {
        return funcRecursoDAO.findRecursosByFuncionario(idFunc);
    }
}