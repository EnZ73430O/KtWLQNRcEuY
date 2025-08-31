// 代码生成时间: 2025-08-31 17:16:37
package com.yourcompany;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * JsonDataFormatter is a simple JSON data formatter using Quarkus framework.
 * It reads a JSON string, formats it and writes the formatted JSON back to a String.
 */
@QuarkusMain
public class JsonDataFormatter implements QuarkusApplication {

    // Entry point for Quarkus application
    @Override
    public int run(String... args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: JsonDataFormatter <inputJsonFilePath> <outputJsonFilePath>");
            return 1;
        }
        String inputJsonFilePath = args[0];
        String outputJsonFilePath = args[1];

        try {
            // Read the JSON input from file
            String jsonInput = new String(Files.readAllBytes(Path.of(inputJsonFilePath)));

            // Format the JSON
            String formattedJson = formatJson(jsonInput);

            // Write the formatted JSON to file
            Files.write(Path.of(outputJsonFilePath), formattedJson.getBytes());

            System.out.println("JSON formatted successfully.");
            return 0;
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return 1;
        }
    }

    /**
     * Formats a JSON string.
     *
     * @param jsonInput the JSON string to format
     * @return the formatted JSON string
     */
    private String formatJson(String jsonInput) {
        try {
            // Create Jsonb instance
            Jsonb jsonb = JsonbBuilder.create();

            // Parse JSON string to Json structure
            Object json = jsonb.fromJson(jsonInput, Object.class);

            // Write Json structure to formatted String
            StringWriter writer = new StringWriter();
            jsonb.toJson(json, writer);
            return writer.toString();
        } catch (JsonbException e) {
            throw new IllegalArgumentException("Invalid JSON input", e);
        }
    }

    /**
     * Main method for manual testing.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JsonDataFormatter formatter = new JsonDataFormatter();
        formatter.run(args);
    }
}
