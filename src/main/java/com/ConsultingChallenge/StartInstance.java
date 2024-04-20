package com.ConsultingChallenge;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//Activates the webhook "ConsultingChallenge" to start the process

public class StartInstance {

    public static void main(String[] args) {
        String URL = "https://bru-2.connectors.camunda.io/53e9327a-4777-4e0a-bb6d-1bb82a6b7f31/inbound/StartAnimalPictureDownload";
      
        // Create HTTP client
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();

        // Send HTTP request and handle response
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            // System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
