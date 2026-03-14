package io.github.Jonathan_Lin_162.hackathon_project.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class App {
    public static void execute(String input) throws IOException {
        
        String modelName = "llama3.1"; 
        String promptText = input;
        
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

        String line;

        System.out.print("Answer: ");

        while ((line = reader.readLine()) != null) {
            JSONObject chunk = new JSONObject(line);

            if (chunk.has("response")) {
                String text = chunk.getString("response");

                for (char c : text.toCharArray()) {
                    System.out.print(c);
                }
            }

            if (chunk.optBoolean("done")) {
                break;
            }
        }

        System.out.println();
        reader.close();
    }
}