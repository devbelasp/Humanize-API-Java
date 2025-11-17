package br.com.fiap.resource;

import br.com.fiap.bo.FuncionarioBO;
import br.com.fiap.to.FuncionarioTO;
import br.com.fiap.to.LoginTO; // Importação essencial para o Login

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
 * implementando operações CRUD e o endpoint de Login.
 */
@Path("/funcionarios")
public class FuncionarioResource {

    private final FuncionarioBO funcionarioBO = new FuncionarioBO();

    /**
     * Cadastra um novo funcionário.
     * @return 201 CREATED (Sucesso) ou 400 BAD REQUEST (E-mail duplicado/validação).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid FuncionarioTO funcionario) {

        FuncionarioTO resultado = funcionarioBO.save(funcionario);
        Response.ResponseBuilder response = null;

        if (resultado != null) {
            // Sucesso: 201 CREATED
            response = Response.created(null);
        } else {
            // Falha: 400 BAD REQUEST
            response = Response.status(400)
                    .entity("Erro ao cadastrar funcionário. O e-mail informado já está cadastrado ou a data é inválida.");
        }

        return response.entity(resultado).build();
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
        Response.ResponseBuilder response = null;

        if (resultado != null) {
            // Sucesso: 200 OK
            response = Response.ok();
        } else {
            // Falha: 401 UNAUTHORIZED
            response = Response.status(401)
                    .entity("Credenciais de login inválidas. Verifique o e-mail e a senha.");
        }

        return response.entity(resultado).build();
    }

    /**
     * Retorna a lista de todos os funcionários.
     * @return 200 OK (com lista) ou 404 NOT FOUND (lista vazia).
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {

        ArrayList<FuncionarioTO> resultado = funcionarioBO.findAll();
        Response.ResponseBuilder response = null;

        if (resultado != null && !resultado.isEmpty()) {
            // Sucesso: 200 OK
            response = Response.ok();
        } else {
            // Não encontrado: 404 NOT FOUND
            response = Response.status(404)
                    .entity("Nenhum funcionário cadastrado.");
        }

        return response.entity(resultado).build();
    }

    /**
     * Busca um funcionário pelo seu ID.
     * @return 200 OK (Sucesso) ou 404 NOT FOUND (Não encontrado).
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCodigo(@PathParam("id") int id) {

        FuncionarioTO resultado = funcionarioBO.findByCodigo(id);
        Response.ResponseBuilder response = null;

        if (resultado != null) {
            // Sucesso: 200 OK
            response = Response.ok();
        } else {
            // Não encontrado: 404 NOT FOUND
            response = Response.status(404)
                    .entity("Funcionário com ID " + id + " não encontrado.");
        }

        return response.entity(resultado).build();
    }

    /**
     * Atualiza os dados de um funcionário existente.
     * @return 200 OK (Sucesso) ou 404 NOT FOUND (ID não encontrado para update).
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid FuncionarioTO funcionario, @PathParam("id") int id) {

        // Garante que o ID da URL seja usado no objeto TO
        funcionario.setId(id);

        FuncionarioTO resultado = funcionarioBO.update(funcionario);
        Response.ResponseBuilder response = null;

        if (resultado != null) {
            // Sucesso: 200 OK
            response = Response.ok();
        } else {
            // Falha: 404 NOT FOUND
            response = Response.status(404)
                    .entity("Erro ao atualizar. Funcionário com ID " + id + " não encontrado.");
        }

        return response.entity(resultado).build();
    }

    /**
     * Exclui um funcionário e suas dependências (em cascata).
     * @return 204 NO CONTENT (Sucesso) ou 404 NOT FOUND (ID não encontrado para exclusão).
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        if (funcionarioBO.delete(id)) {
            // Sucesso: 204 NO CONTENT
            return Response.noContent().build();
        } else {
            // Falha: 404 NOT FOUND
            return Response.status(404)
                    .entity("Funcionário com ID " + id + " não encontrado para exclusão.").build();
        }
    }
}