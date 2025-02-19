package com.example.deepseekapi.Controller;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengqi
 * @date 2025/2/18 下午4:40
 */
@RestController
public class OllamaClientController {
    @Autowired
    @Qualifier("ollamaChatClient")
    private OllamaChatClient ollamaChatClient;

    @GetMapping("/ollama/chat/v1")
    public String ollamaChat(@RequestParam String msg) {
        return this.ollamaChatClient.call(msg);
    }

    @GetMapping("/ollama/chat/v2")
    public Object ollamaChatV2(@RequestParam String msg) {
        Prompt prompt = new Prompt(msg);
        ChatResponse chatResponse = ollamaChatClient.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping("/ollama/chat/v3")
    public Object ollamaChatV3(@RequestParam String msg) {
        Prompt prompt = new Prompt(
                msg,
                OllamaOptions.create()
                        .withModel("deepseek-r1:8b")
                        .withTemperature(0.4F));
        ChatResponse chatResponse = ollamaChatClient.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }
}
