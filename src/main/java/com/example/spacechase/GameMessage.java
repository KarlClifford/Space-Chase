package com.example.spacechase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * This class handles fetching and verification of
 * messages from the message of the day API.
 *
 * @author Karl Clifford
 * @version 1.0.0
 */
public class GameMessage {

    /**
     * Fetches the message from the MOTD endpoint.
     * @return String message.
     */
    private static String getMessage() {
        // Instantiate the client to use for the request.
        HttpClient client = HttpClient.newHttpClient();

        // Create the request for the MOTD endpoint.
        var request = HttpRequest.newBuilder(
                        URI.create("http://cswebcat.swansea.ac.uk/puzzle"))
                .header("accept", "plain/text")
                .build();

        HttpResponse<String> response;
        // Try to send the request to the server.
        try {
            response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            // I/O error occurred or the program was interrupted.
            throw new Error("The program was interrupted "
                    + "before the MOTD get request could be completed");
        }

        return response.body();
    }
}
