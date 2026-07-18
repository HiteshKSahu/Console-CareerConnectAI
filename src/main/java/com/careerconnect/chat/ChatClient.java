package com.careerconnect.chat;

/**
 * Adapter interface for LLM communication.
 * The application talks to this interface, not directly to Ollama.
 * This makes it easy to swap out Ollama for another LLM provider later.
 */
public interface ChatClient {

    /**
     * Sends a system prompt and user message to the LLM and returns the response text.
     * Throws ServiceUnavailableException if the LLM service cannot be reached.
     */
    String chat(String systemPrompt, String userMessage);
}
