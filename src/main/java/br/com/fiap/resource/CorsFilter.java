package br.com.fiap.resource;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Implementa o filtro de CORS (Cross-Origin Resource Sharing).
 * Adiciona cabeçalhos de permissão na resposta HTTP para permitir
 * que o Front-End (em domínio diferente) acesse a API.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) throws IOException {

        // Permite acesso de qualquer origem
        response.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Define os cabeçalhos permitidos na requisição
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Permite o envio de credenciais (se necessário)
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // Define os métodos HTTP permitidos (GET, POST, PUT, DELETE, OPTIONS, HEAD)
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}