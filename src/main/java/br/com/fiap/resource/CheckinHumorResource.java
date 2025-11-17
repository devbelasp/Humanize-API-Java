package br.com.fiap.resource;

import br.com.fiap.bo.CheckinHumorBO;
import br.com.fiap.to.CheckinHumorTO;
import br.com.fiap.to.RelatorioHumorTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Gerencia o endpoint RESTful (/checkins) para o Questionário de Humor,
 * controlando o registro e a consulta analítica dos dados.
 */
@Path("/checkins")
public class CheckinHumorResource {

    private final CheckinHumorBO bo = new CheckinHumorBO();

    /**
     * Registra o check-in de humor do funcionário.
     * Implementa a regra de unicidade diária via BO.
     * @return 201 CREATED (Sucesso) ou 400 BAD REQUEST (Check-in duplicado ou validação).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid CheckinHumorTO checkin) {

        CheckinHumorTO resultado = bo.save(checkin);

        if (resultado != null) {
            // Sucesso na criação: 201 CREATED
            return Response.created(null).entity(resultado).build();
        } else {
            // Falha na regra de negócio (Check-in duplicado)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao registrar check-in. Você já registrou o humor para esta data.")
                    .build();
        }
    }

    /**
     * Retorna os dados agregados (média de humor por equipe) para visualização no Dashboard.
     * @return 200 OK (com lista) ou 404 NOT FOUND (sem dados para análise).
     */
    @GET
    @Path("/analise")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboardAnalysis() {
        ArrayList<RelatorioHumorTO> lista = bo.consultarRelatorioHumor();

        if (lista != null && !lista.isEmpty()) {
            // Sucesso: 200 OK
            return Response.ok(lista).build();
        } else {
            // Sem dados: 404 NOT FOUND
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum dado de análise encontrado. Realize check-ins primeiro.")
                    .build();
        }
    }

    /**
     * Retorna o histórico BRUTO de todos os check-ins (dados de auditoria).
     * @return 200 OK (com lista) ou 404 NOT FOUND (lista vazia).
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<CheckinHumorTO> lista = bo.findAll();

        if (lista != null && !lista.isEmpty()) {
            // Sucesso: 200 OK
            return Response.ok(lista).build();
        } else {
            // Lista vazia: 404 NOT FOUND
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum registro de humor encontrado no histórico.")
                    .build();
        }
    }
}