package io.github.Jonathan_Lin_162.hackathon_project.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.json.JSONObject;

public class App {

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        OllamaChat chat = new OllamaChat();

        System.out.println("AI Chat started. Type 'exit' to quit the program.");

        while (true) {

            System.out.print("You: ");
            String userInput = scan.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Add user input to history
            chat.addUserMessage(userInput);

            // Prepare prompt including conversation history
            String promptText = chat.buildPrompt();

            // Send streaming request
            String aiResponse = sendStreamingRequest(promptText, "llama3.1");

            System.out.println("\nAI:\n");

            // Print typing effect
            for (char c : aiResponse.toCharArray()) {
                System.out.print(c);
                Thread.sleep(15); // adjust speed
            }
            System.out.println("\n");

            // Add assistant response to history
            chat.addAssistantMessage(aiResponse);
        }

        scan.close();
    }

    private static String sendStreamingRequest(String promptText, String modelName) throws Exception {

        URL url = new URL("http://localhost:11434/api/generate");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        JSONObject request = new JSONObject();
        request.put("model", modelName);
        request.put("prompt", promptText);
        request.put("stream", true);

        String jsonInput = request.toString();

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
        );

        StringBuilder fullResponse = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            JSONObject chunk = new JSONObject(line);

            if (chunk.has("response")) {
                String text = chunk.getString("response");
                fullResponse.append(text);
            }

            if (chunk.optBoolean("done")) {
                break;
            }
        }

        reader.close();
        return fullResponse.toString();
    }
}
