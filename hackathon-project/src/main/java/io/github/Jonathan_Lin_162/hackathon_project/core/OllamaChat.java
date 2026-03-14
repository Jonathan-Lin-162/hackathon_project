package io.github.Jonathan_Lin_162.hackathon_project.core;

import java.util.ArrayList;
import java.util.List;

public class OllamaChat {

    private final List<String> history = new ArrayList<>();

    private final String systemPrompt =
            "You are an AI assistant.\n" +
            "Keep answers concise." +
            "Provide an example if necessary";

    public void addUserMessage(String message) {
        history.add("User: " + message);
    }

    public void addAssistantMessage(String message) {
        history.add("AI: " + message);
    }

    public String buildPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append(systemPrompt).append("\n\n");
        for (String msg : history) {
            sb.append(msg).append("\n");
        }
        sb.append("AI: "); // AI will continue from here
        return sb.toString();
    }
}
