package br.com.fiap.bo;

import br.com.fiap.dao.FuncionarioDAO;
import br.com.fiap.dao.CheckinHumorDAO;
import br.com.fiap.to.FuncionarioTO;
import java.util.ArrayList;

/**
 * Gerencia a lógica de negócio e orquestra as operações CRUD para a entidade Funcionário.
 * Inclui as regras de unicidade de e-mail e exclusão em cascata completa.
 */
public class FuncionarioBO {

    private final FuncionarioDAO dao = new FuncionarioDAO();
    private final CheckinHumorDAO checkinDAO = new CheckinHumorDAO();

    /**
     * Salva um novo Funcionário, verificando a unicidade do e-mail.
     */
    public FuncionarioTO save(FuncionarioTO funcionario) {
        if (dao.findByEmail(funcionario.getEmail()) != null) {
            return null;
        }
        return dao.save(funcionario);
    }

    /**
     * Realiza o login, buscando o funcionário pelo e-mail e validando a senha.
     */
    public FuncionarioTO login(String email, String senha) {
        FuncionarioTO funcionario = dao.buscarPorLogin(email, senha);
        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            return funcionario;
        }
        return null;
    }

    /**
     * Busca todos os funcionários cadastrados.
     */
    public ArrayList<FuncionarioTO> findAll() {
        return dao.findAll();
    }

    /**
     * Busca um funcionário pelo seu código (ID).
     */
    public FuncionarioTO findByCodigo(int id) {
        return dao.findByCodigo(id);
    }

    /**
     * Atualiza um funcionário existente, verificando a unicidade do e-mail.
     */
    public FuncionarioTO update(FuncionarioTO funcionario) {
        FuncionarioTO existente = dao.findByEmail(funcionario.getEmail());

        if (existente != null && existente.getId() != funcionario.getId()) {
            return null;
        }

        return dao.update(funcionario);
    }

    /**
     * Exclui um funcionário, limpando todas as suas dependências (check-ins e recursos) primeiro.
     */
    public boolean delete(int id) {

        // Limpa registros da tabela T_H_HUMOR (Check-ins de humor)
        checkinDAO.deleteByFuncionarioId(id);

        // Limpa registros da tabela T_H_FUNC_RECURSO (Recursos associados)
        dao.deleteRecursosAssociados(id);

        // Exclui o registro principal (T_H_FUNCIONARIO)
        return dao.delete(id);
    }
}