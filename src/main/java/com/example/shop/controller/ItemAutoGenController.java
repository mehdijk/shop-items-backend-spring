package com.example.shop.controller;

import com.example.shop.service.LLMService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/LLM")
@CrossOrigin(origins = {"*"})
public class ItemAutoGenController {

    private final LLMService llmService;

    public ItemAutoGenController(LLMService llmService) {
        this.llmService = llmService;
    }

    @GetMapping("/generate-description")
    public String getItemDescription(@RequestParam String itemName) {
        try {
            return llmService.getItemDescription(itemName);
        } catch (Exception e) {
            return "Error generating description: " + e.getMessage();
        }
    }
}
