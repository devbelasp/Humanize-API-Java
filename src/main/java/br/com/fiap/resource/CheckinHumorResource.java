package br.com.fiap.resource;

import br.com.fiap.bo.CheckinHumorBO;
import br.com.fiap.bo.FuncionarioBO;
import br.com.fiap.to.CheckinHumorTO;
import br.com.fiap.to.RelatorioHumorTO;
import br.com.fiap.to.CheckinHumorAnonimoTO;
import br.com.fiap.to.FuncionarioTO;
import br.com.fiap.exception.AcessoNegadoException;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gerencia o endpoint RESTful (/checkins) para o Questionário de Humor,
 * controlando o registro e a consulta analítica dos dados.
 */
@Path("/checkins")
public class CheckinHumorResource {

    private final CheckinHumorBO bo = new CheckinHumorBO();
    private final FuncionarioBO funcionarioBO = new FuncionarioBO(); // Instância adicionada

    // ID de Funções que têm acesso ao Dashboard (Gestores: 3, 4; RH: 5)
    private static final int ID_FUNCAO_RH = 5;
    private static final int ID_FUNCAO_TECH_LEAD = 3;
    private static final int ID_FUNCAO_GERENTE = 4;

    /**
     * Registra o check-in de humor do funcionário.
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
     * Implementa filtro de visualização por perfil (Gestor/RH).
     * @param funcionarioId ID do funcionário logado.
     * @return 200 OK (com lista filtrada) ou 403 FORBIDDEN (sem permissão).
     */
    @GET
    @Path("/analise/{funcionarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboardAnalysis(@PathParam("funcionarioId") int funcionarioId) {

        // Valiada e Buscar o Perfil do Solicitante
        FuncionarioTO solicitante = funcionarioBO.findByCodigo(funcionarioId);

        if (solicitante == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuário não autenticado ou não encontrado.")
                    .build();
        }

        int idFuncao = solicitante.getIdFuncao();
        int idEquipe = solicitante.getEquipeId();

        // Define Permissão de Acesso ao Dashboard (Apenas RH e Gestores)
        if (idFuncao != ID_FUNCAO_RH && idFuncao != ID_FUNCAO_TECH_LEAD && idFuncao != ID_FUNCAO_GERENTE) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Acesso negado. Apenas gestores e RH podem visualizar o dashboard de análise.")
                    .build();
        }

        // Busca Dados Agregados
        ArrayList<RelatorioHumorTO> listaCompleta = bo.consultarRelatorioHumor();

        if (listaCompleta == null || listaCompleta.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum dado de análise encontrado para o Dashboard.")
                    .build();
        }

        List<RelatorioHumorTO> listaFiltrada;

        // Aplica Lógica de Filtragem por Perfil
        if (idFuncao == ID_FUNCAO_RH) {
            // Perfil RH (ID_FUNCAO = 5): Acesso a Todas as Equipes
            listaFiltrada = listaCompleta;

        } else {
            // Perfil Gestor (ID_FUNCAO = 3 ou 4): Acesso Apenas à Sua Equipe
            listaFiltrada = listaCompleta.stream()
                    .filter(relatorio -> relatorio.getEquipeId() == idEquipe)
                    .collect(Collectors.toList());
        }

        // Retorna Resultado
        if (!listaFiltrada.isEmpty()) {
            return Response.ok(listaFiltrada).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum dado de análise encontrado para o seu perfil/equipe.")
                    .build();
        }
    }

    /**
     * Retorna o histórico BRUTO de todos os check-ins (dados de auditoria).
     * Implementa a REGRA: Apenas RH pode consultar. O DTO de retorno é ANONIMIZADO.
     * @param solicitanteId ID do funcionário que está requisitando o histórico.
     * @return 200 OK (com lista anonimizada) ou 403 FORBIDDEN (sem permissão).
     */
    @GET
    @Path("/{solicitanteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@PathParam("solicitanteId") int solicitanteId) {

        ArrayList<CheckinHumorAnonimoTO> lista;

        try {
            // Chama o método BO que retorna a lista sem o ID_FUNC
            lista = bo.findAllAnonimo(solicitanteId);

        } catch (AcessoNegadoException e) {
            // Captura exceção de Autorização e retorna 403 FORBIDDEN
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(e.getMessage())
                    .build();
        }

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