package br.com.fiap.resource;

import br.com.fiap.bo.RecursoBemEstarBO;
import br.com.fiap.to.RecursoBemEstarTO;
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
 * Gerencia o endpoint RESTful (/recursos) para a entidade RecursoBemEstar,
 * implementando as operações CRUD.
 */
@Path("/recursos")
public class RecursoBemEstarResource {

    private final RecursoBemEstarBO bo = new RecursoBemEstarBO();

    /**
     * Cadastra um novo recurso de bem-estar.
     * @return 201 CREATED (Sucesso) ou 400 BAD REQUEST (Falha).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid RecursoBemEstarTO recurso) {

        RecursoBemEstarTO resultado = bo.save(recurso);

        if (resultado != null) {
            // Sucesso na criação: 201 CREATED
            return Response.created(null).entity(resultado).build();
        } else {
            // Falha na persistência (retorno null do DAO/BO)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao cadastrar recurso de bem-estar.")
                    .build();
        }
    }

    /**
     * Retorna todos os recursos de bem-estar cadastrados.
     * @return 200 OK (com lista) ou 404 NOT FOUND (lista vazia).
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<RecursoBemEstarTO> lista = bo.findAll();

        if (lista != null && !lista.isEmpty()) {
            return Response.ok(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum recurso de bem-estar encontrado.")
                    .build();
        }
    }

    /**
     * Busca e retorna um recurso específico pelo seu ID.
     * @return 200 OK (Sucesso) ou 404 NOT FOUND (Não encontrado).
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") int id) {

        RecursoBemEstarTO resultado = bo.findById(id);

        if (resultado != null) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Recurso com ID " + id + " não encontrado.")
                    .build();
        }
    }

    /**
     * Atualiza um recurso de bem-estar existente.
     * @return 200 OK (Sucesso) ou 404 NOT FOUND (ID não existe).
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid RecursoBemEstarTO recurso, @PathParam("id") int id) {

        recurso.setId(id);
        RecursoBemEstarTO resultado = bo.update(recurso);

        if (resultado != null) {
            // Sucesso na atualização: 200 OK
            return Response.ok(resultado).build();
        } else {
            // Falha (ID não encontrado para update)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Recurso com ID " + id + " não encontrado para atualização.")
                    .build();
        }
    }

    /**
     * Exclui um recurso pelo seu ID.
     * @return 204 NO CONTENT (Sucesso) ou 404 NOT FOUND (ID não existe).
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        boolean sucesso = bo.delete(id);

        if (sucesso) {
            // Sucesso na exclusão: 204 NO CONTENT
            return Response.noContent().build();
        } else {
            // Falha (ID não encontrado)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Recurso com ID " + id + " não encontrado para exclusão.")
                    .build();
        }
    }
}