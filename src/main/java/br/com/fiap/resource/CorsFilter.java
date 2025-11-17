package br.com.fiap.resource;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * Filtro CORS (Cross-Origin Resource Sharing).
 * Adiciona cabeçalhos de permissão para que aplicações front-end (de domínios diferentes)
 * possam consumir a API Java, resolvendo problemas de segurança de navegador.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    /**
     * Adiciona os cabeçalhos HTTP necessários para permitir requisições de qualquer origem.
     */
    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }

}