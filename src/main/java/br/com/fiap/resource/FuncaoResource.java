package br.com.fiap.resource;

import br.com.fiap.bo.FuncaoBO;
import br.com.fiap.to.FuncaoTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Gerencia o endpoint RESTful (/funcoes) para a entidade Função.
 * Implementa apenas a consulta (GET) para retornar a lista de referência (perfis/cargos).
 */
@Path("/funcoes")
public class FuncaoResource {

    private final FuncaoBO bo = new FuncaoBO();

    /**
     * Retorna a lista de todas as funções (perfis) para uso em dropdowns/seleções.
     * Path: GET /funcoes
     * @return 200 OK (com lista) ou 404 NOT FOUND (lista vazia).
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<FuncaoTO> lista = bo.findAll();

        if (lista != null && !lista.isEmpty()) {
            // Sucesso: 200 OK
            return Response.ok(lista).build();
        } else {
            // Lista vazia: 404 NOT FOUND
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhuma função de referência encontrada.")
                    .build();
        }
    }
}