package io.github.Jonathan_Lin_162.hackathon_project.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class App {

	private static OllamaChat chat = new OllamaChat();
	
	private static String modelName = "llama3.1";

    public static void execute(String input) throws Exception {

            chat.addUserMessage(input);

            String promptText = chat.buildPrompt();
            
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
            System.out.println("\nAI:");
            while ((line = reader.readLine()) != null) {
                JSONObject chunk = new JSONObject(line);

                if (chunk.has("response")) {
                    String text = chunk.getString("response");
                    fullResponse.append(text);
                    for (char c : text.toCharArray()) {
                        System.out.print(c);
                    }
                }

                if (chunk.optBoolean("done")) {
                    break;
                }
            }

            reader.close();

            String aiResponse = fullResponse.toString();
            
            System.out.println("\n");

            chat.addAssistantMessage(aiResponse);
        }
}
