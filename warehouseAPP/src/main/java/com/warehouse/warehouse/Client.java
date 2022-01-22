package com.warehouse.warehouse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .GET();
        final HttpRequest request = requestBuilder.build();
        final HttpClient httpClient = HttpClient.newHttpClient();
        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            System.out.println(response.body());
        } else if (response.statusCode() == 404) {
            System.out.println("The server can not find the requested resource.");
        } else if(response.statusCode() == 405) {
            System.out.println("The request method is known by the server but is not supported by the target resource.");
        } else {
            System.out.printf("Status: %d%nBody: %s", response.statusCode(), response.body());
        }
    }

}
