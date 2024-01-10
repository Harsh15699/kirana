package com.jar.kiranaregister.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for currency conversion using an external API.
 */
public class CurrencyConverterUtil {

    private static String url = "https://api.fxratesapi.com/latest";

    /**
     * Converts the given dollar value to rupees using the latest exchange rate from the external API.
     *
     * @param dollarValue The amount in dollars to be converted.
     * @return The equivalent amount in rupees.
     */
    public static double convertDollarToRupee(Double dollarValue) {
        Double INRExchangeRate = 0.0;

        try {
            // Make a request to the external API to get the latest exchange rates
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response to extract the exchange rate for INR
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode data = jsonNode.get("rates");
            INRExchangeRate = data.get("INR").doubleValue();

        } catch (InterruptedException | JsonProcessingException e) {
            e.printStackTrace(); // Handle exceptions appropriately in a real-world scenario
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately in a real-world scenario
        }

        // Convert the dollar value to rupees using the retrieved exchange rate
        return dollarValue * INRExchangeRate;
    }
}
