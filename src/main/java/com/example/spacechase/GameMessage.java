package com.example.spacechase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class handles fetching and verification of
 * messages from the message of the day API.
 * @author Karl Clifford
 * @version 1.0.0
 */
public class GameMessage {
    /**
     * This is the minimum ascii value we can use
     * when decoding the message.
     */
    static final int LOWEST_ASCII_VALUE = 65;

    /**
     * This is the greatest ascii value we can use
     * when decoding the message.
     */
    static final int HIGHEST_ASCII_VALUE = 90;


    /**
     * Fetches a message from the MOTD endpoint.
     * @param url The endpoint to query.
     * @return String message.
     * @throws IOException When a connection to the endpoint can't be
     * established.
     * @throws InterruptedException When the connection to the endpoint is
     * interrupted.
     */
    private static String getMessage(String url)
            throws IOException, InterruptedException {
        // Instantiate the client to use for the request.
        HttpClient client = HttpClient.newHttpClient();

        // Create the request for the MOTD endpoint.
        var request = HttpRequest.newBuilder(
                        URI.create(url))
                .header("accept", "plain/text")
                .build();

        HttpResponse<String> response;
        // Send the request to the server and store the response.
        response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    /**
     * Takes the key and decodes it.
     * @param key The key retrieved from the MOTD endpoint.
     * @return String The unscrambled key.
     */
    private static String decodeKey(String key) {
        // Split the key into an array of chars.
        final char[] splitMessage = key.toCharArray();

        // Initialise a string builder to append sorted messages to.
        StringBuilder stringBuilder = new StringBuilder();

        /*
         * Iterate through the scrambled key and move characters forwards
         * or backwards depending on the MOTD rules.
         */
        for (int i = 0; i < splitMessage.length; i++) {
            // Store the ASCII value of the current character.
            int ascii = splitMessage[i];
            int index = i;
            boolean sorting = true;
            /*
             * The first value should be decremented by the same value as its
             * index in splitMessage.
             */
            while (sorting) {
                // Reset the stored ascii value to Z when we drop bellow A.
                if (ascii < LOWEST_ASCII_VALUE) {
                    ascii = HIGHEST_ASCII_VALUE;
                }
                // The index isn't 0, the value isn't sorted yet.
                if (index >= 0) {
                    ascii--;
                    index--;
                } else {
                    // The value has been sorted, store it.
                    stringBuilder.append((char) ascii);
                    sorting = false;
                }
            }

            // Move onto the next character.
            i++;

            // Check that we actually have another character to sort.
            if (i < splitMessage.length) {
                // Store the ASCII value of the current character.
                ascii = splitMessage[i];
                sorting = true;
                index = i;
                /*
                 * The second value should be incremented by the same value as
                 * its index in splitMessage.
                 */
                while (sorting) {
                    // Reset the stored ascii value to A when we go above Z.
                    if (ascii > HIGHEST_ASCII_VALUE) {
                        ascii = LOWEST_ASCII_VALUE;
                    }
                    // The index isn't 0, the value isn't sorted yet.
                    if (index >= 0) {
                        ascii++;
                        index--;
                    } else {
                        // The value has been sorted, store it.
                        stringBuilder.append((char) ascii);
                        sorting = false;
                    }
                }
            }
        }

        // Add the required suffix to the end of the message.
        stringBuilder.append("CS-230");
        // Store the length of the key result to be added as a prefix.
        int messageLength = stringBuilder.length();

        return "%d%s".formatted(messageLength, stringBuilder);
    }

    /**
     * Fetches the message of the day.
     * @return String message of the day.
     * @throws IOException When a connection to the endpoint can't be
     * established.
     * @throws InterruptedException When the connection to the endpoint is
     * interrupted.
     */
    public static String fetch() throws IOException, InterruptedException {
        // Get the key and store it.
        final String key = decodeKey(
                getMessage("http://cswebcat.swansea.ac.uk/puzzle"));
        // Output the message.
        return getMessage(
                "http://cswebcat.swansea.ac.uk/message?solution=" + key);
    }
}
