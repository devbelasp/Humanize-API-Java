package br.com.fiap.exception;

/**
 * Exceção de Negócio (Checked Exception) utilizada para indicar que
 * o usuário logado (solicitante) não possui as permissões necessárias
 * (role/função) para executar uma determinada operação, como cadastrar
 * um funcionário ou acessar dados restritos.
 */
public class AcessoNegadoException extends RuntimeException {

    public AcessoNegadoException(String message) {
        super(message);
    }

    public AcessoNegadoException(String message, Throwable cause) {
        super(message, cause);
    }
}