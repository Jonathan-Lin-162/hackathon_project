package io.github.Jonathan_Lin_162.hackathon_project.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class App {

	private static OllamaChat chat = new OllamaChat();
	
	private static String modelName = "llama3.1";

	public static void execute(String input, java.util.function.Consumer<String> onChunk) throws Exception {

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

	    try (OutputStream os = conn.getOutputStream()) {
	        os.write(request.toString().getBytes(StandardCharsets.UTF_8));
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

	            onChunk.accept(text);   // send chunk to UI
	        }

	        if (chunk.optBoolean("done")) {
	            break;
	        }
	    }

	    reader.close();

	    chat.addAssistantMessage(fullResponse.toString());
	}
}
