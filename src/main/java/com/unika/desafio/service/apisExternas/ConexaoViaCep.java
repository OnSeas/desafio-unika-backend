package com.unika.desafio.service.apisExternas;

import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexaoViaCep {
    final String ENDERECO = "http://viacep.com.br/ws/";
    final String TIPO = "/json/";

    public HttpResponse<String> getEnderecoPeloCep(String cep) throws IOException, InterruptedException {

        if (cep == null || cep.isBlank()){
            throw new BusinessException(ErrorCode.CEP_INVALIDO);
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDERECO + cep + TIPO))
                .build();
        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 400 || response.body().contains("\"erro\": \"true\"")){
            throw new BusinessException(ErrorCode.CEP_INVALIDO);
        }

        return response;
    }
}
