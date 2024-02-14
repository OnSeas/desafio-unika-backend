package com.unika.desafio.service.apisExternas;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexaoViaCep {
    final String ENDERECO = "http://viacep.com.br/ws/";
    final String TIPO = "/json/";

    public HttpResponse<String> getEnderecoPeloCep(String cep) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDERECO + cep + TIPO))
                .build();
        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }
}
