package com.example.shop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class LLMService {

    @Value("${llm.api.key}")
    private String apiKey;

    @Value("${llm.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public LLMService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getItemDescription(String itemName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String payload = """
        {
            "model": "meta-llama/llama-3.2-3b-instruct:free",
            "messages": [
                {
                    "role": "user",
                    "content": "Write a short description for the product (Please provide a clean, concise description without any special characters like asterisks (**) or new line characters.): %s"
                }
            ],
            "max_tokens": 65
        }
        """.formatted(itemName);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
            return parseDescription(response.getBody());
        } catch (Exception e) {
            throw new Exception("Error while calling LLM API: " + e.getMessage());
        }
    }

    private String parseDescription(String responseBody) throws Exception {
        // Extracting the description from the response
        try {
            // Use a JSON library to parse the response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && !choicesNode.isEmpty()) {
                return choicesNode.get(0).path("message").path("content").asText();
            }
            throw new Exception("Invalid response format");
        } catch (Exception e) {
            throw new Exception("Error parsing the response: " + e.getMessage());
        }
    }
}
