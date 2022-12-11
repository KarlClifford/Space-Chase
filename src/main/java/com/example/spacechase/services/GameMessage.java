package com.example.spacechase.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

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

        // Initialise a stack to add the scrambled message to.
        Queue<String> scrambledStack = new LinkedList<>();

        /*
         * Add all the characters from
         * the message into the scrambled stack for sorting.
         */
        for (char character : splitMessage) {
            scrambledStack.add(String.valueOf(character));
        }

        /*
         * Iterate through the stack and convert the characters according to the
         * key rules. The first value should be decremented by the same number
         * that is its index. The second value should be incremented by the same
         * number as its index.
         */
        int index = 1;
        while (!scrambledStack.isEmpty()) {
            int ascii = scrambledStack.remove().charAt(0);

            ascii -= index;
            int result =
                    (ascii < LOWEST_ASCII_VALUE)
                            ?
                            (HIGHEST_ASCII_VALUE - (LOWEST_ASCII_VALUE - ascii)
                                    + 1)
                            : ascii;

            stringBuilder.append((char) result);

            // Move onto the next character.
            index++;

            // Check that we still have another value to sort.
            if (!scrambledStack.isEmpty()) {
                ascii = scrambledStack.remove().charAt(0);

                ascii += index;
                result =
                        (ascii > HIGHEST_ASCII_VALUE) ? (LOWEST_ASCII_VALUE
                                + (ascii - HIGHEST_ASCII_VALUE) - 1) : ascii;
                stringBuilder.append((char) result);
            }
            // Move onto the next character.
            index++;
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
