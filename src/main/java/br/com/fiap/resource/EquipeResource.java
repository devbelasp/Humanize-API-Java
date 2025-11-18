package br.com.fiap.resource;

import br.com.fiap.bo.EquipeBO;
import br.com.fiap.to.EquipeTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Gerencia o endpoint RESTful (/equipes) para a entidade Equipe.
 * Implementa apenas a consulta (GET) para retornar a lista de referência.
 */
@Path("/equipes")
public class EquipeResource {

    private final EquipeBO bo = new EquipeBO();

    /**
     * Retorna a lista de todas as equipes para uso em dropdowns/seleções.
     * Path: GET /equipes
     * @return 200 OK (com lista) ou 404 NOT FOUND (lista vazia).
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<EquipeTO> lista = bo.findAll();

        if (lista != null && !lista.isEmpty()) {
            // Sucesso: 200 OK
            return Response.ok(lista).build();
        } else {
            // Lista vazia: 404 NOT FOUND
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhuma equipe de referência encontrada.")
                    .build();
        }
    }
}