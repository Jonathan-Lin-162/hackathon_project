package io.github.Jonathan_Lin_162.hackathon_project.core;

import java.io.*;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws IOException {

        String modelName = "llama3.1";
        Scanner scan = new Scanner(System.in);
        String promptText;
        String input;
        String systemPrompt = "You are an AI assistant.\n"
        		+ "\n"
        		+ "        		Always answer using this format:\n"
        		+ "\n"
        		+ "        		Definition:\n"
        		+ "        		<short definition>\n"
        		+ "\n"
        		+ "        		Key Points:\n"
        		+ "        		- point 1\n"
        		+ "        		- point 2\n"
        		+ "        		- point 3\n"
        		+ "\n"
        		+ "        		Example:\n"
        		+ "        		<short example>\n"
        		+ "\n"
        		+ "        		Keep the answer concise and accurate.";
        System.out.println("AI Chat started. Type 'exit' to quit the program.");
        System.out.print("You: ");
    	
    	promptText = systemPrompt + scan.nextLine();
        while (true) {

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
            
        	System.out.print("You: ");
        	input = scan.nextLine();
        	
        	if (input.equalsIgnoreCase("exit")) {
        		System.out.println("Goodbye!");
        		break;
        	}
        	promptText = systemPrompt + input;
        }
        scan.close();
    }

}