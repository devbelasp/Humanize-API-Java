package br.com.fiap.resource;

import br.com.fiap.bo.FuncionarioBO;
import br.com.fiap.to.FuncionarioTO;
import br.com.fiap.to.LoginTO;
import br.com.fiap.to.RecursoBemEstarTO;
import br.com.fiap.exception.AcessoNegadoException;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

/**
 * Gerencia o endpoint RESTful (/funcionarios) para a entidade Funcionario,
 * implementando operações CRUD, Login e gerenciamento de Recursos de Bem-Estar.
 */
@Path("/funcionarios")
public class FuncionarioResource {

    private final FuncionarioBO funcionarioBO = new FuncionarioBO();

    /**
     * Cadastra um novo funcionário.
     * Implementa a REGRA: Apenas o RH pode cadastrar.
     * @return 201 CREATED, 403 FORBIDDEN (Sem Permissão) ou 400 BAD REQUEST.
     */
    @POST
    @Path("/cadastro/{solicitanteId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid FuncionarioTO novoFuncionario, @PathParam("solicitanteId") int solicitanteId) {

        FuncionarioTO resultado = null;

        try {
            // Delega a lógica de Autorização (ID_FUNCAO = 5) para a camada BO
            resultado = funcionarioBO.cadastrarNovoFuncionario(novoFuncionario, solicitanteId);

            // Se não houve exceção, retorna 201 CREATED
            return Response.created(null).entity(resultado).build();

        } catch (AcessoNegadoException e) {
            // Captura exceção de Autorização do BO e retorna 403 FORBIDDEN
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(e.getMessage())
                    .build();

        } catch (RuntimeException e) {
            // Captura outras exceções (e-mail duplicado, BD, etc.) e retorna 400 BAD REQUEST
            return Response.status(400)
                    .entity("Erro ao cadastrar funcionário: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Realiza o login do funcionário.
     * @return 200 OK (Sucesso) ou 401 UNAUTHORIZED (Credenciais inválidas).
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginTO loginData) {

        FuncionarioTO resultado = funcionarioBO.login(loginData.getEmail(), loginData.getSenha());

        if (resultado != null) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(401)
                    .entity("Credenciais de login inválidas. Verifique o e-mail e a senha.")
                    .build();
        }
    }

    /**
     * Retorna a lista de todos os funcionários.
     * @return 200 OK ou 404 NOT FOUND.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {

        ArrayList<FuncionarioTO> resultado = funcionarioBO.findAll();

        if (resultado != null && !resultado.isEmpty()) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(404)
                    .entity("Nenhum funcionário cadastrado.")
                    .build();
        }
    }

    /**
     * Busca um funcionário pelo seu ID.
     * @return 200 OK ou 404 NOT FOUND.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCodigo(@PathParam("id") int id) {

        FuncionarioTO resultado = funcionarioBO.findByCodigo(id);

        if (resultado != null) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(404)
                    .entity("Funcionário com ID " + id + " não encontrado.")
                    .build();
        }
    }

    /**
     * Atualiza os dados de um funcionário existente.
     * @return 200 OK ou 404 NOT FOUND.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid FuncionarioTO funcionario, @PathParam("id") int id) {

        funcionario.setId(id);

        FuncionarioTO resultado = funcionarioBO.update(funcionario);

        if (resultado != null) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(404)
                    .entity("Erro ao atualizar. Funcionário com ID " + id + " não encontrado.")
                    .build();
        }
    }

    /**
     * Exclui um funcionário e suas dependências.
     * @return 204 NO CONTENT ou 404 NOT FOUND.
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        if (funcionarioBO.delete(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(404)
                    .entity("Funcionário com ID " + id + " não encontrado para exclusão.")
                    .build();
        }
    }

    /**
     * Lista todos os recursos de bem-estar associados a um funcionário (Favoritos).
     * Path: /funcionarios/{id}/recursos
     * @return 200 OK com lista de recursos ou 404 NOT FOUND.
     */
    @GET
    @Path("/{id}/recursos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRecursosFavoritos(@PathParam("id") int id) {
        ArrayList<RecursoBemEstarTO> lista = funcionarioBO.listarRecursosFavoritos(id);

        if (lista != null && !lista.isEmpty()) {
            return Response.ok(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum recurso favorito encontrado para o funcionário com ID " + id + ".")
                    .build();
        }
    }

    /**
     * Associa um recurso a um funcionário (Adicionar aos Favoritos).
     * Path: /funcionarios/{idFunc}/recursos/{idRecurso}
     * @return 201 CREATED ou 400 BAD REQUEST (Falha ou Duplicidade).
     */
    @POST
    @Path("/{idFunc}/recursos/{idRecurso}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response associarRecurso(@PathParam("idFunc") int idFunc, @PathParam("idRecurso") int idRecurso) {

        boolean sucesso = funcionarioBO.associarRecurso(idFunc, idRecurso);

        if (sucesso) {
            return Response.status(Response.Status.CREATED)
                    .entity("Recurso " + idRecurso + " associado ao funcionário " + idFunc + " com sucesso.")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao associar recurso. Verifique se o recurso já está na lista ou se os IDs são válidos.")
                    .build();
        }
    }

    /**
     * Desassocia um recurso de um funcionário (Remover dos Favoritos).
     * Path: /funcionarios/{idFunc}/recursos/{idRecurso}
     * @return 204 NO CONTENT ou 404 NOT FOUND (Associação não existe).
     */
    @DELETE
    @Path("/{idFunc}/recursos/{idRecurso}")
    public Response desassociarRecurso(@PathParam("idFunc") int idFunc, @PathParam("idRecurso") int idRecurso) {

        boolean sucesso = funcionarioBO.desassociarRecurso(idFunc, idRecurso);

        if (sucesso) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("A associação entre funcionário " + idFunc + " e recurso " + idRecurso + " não foi encontrada.")
                    .build();
        }
    }
}