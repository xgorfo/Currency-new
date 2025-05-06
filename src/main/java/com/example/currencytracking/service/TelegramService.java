package com.example.currencytracking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramService {

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.chat-id}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        try {
            String url = "https://api.telegram.org/bot" + botToken +
                         "/sendMessage?chat_id=" + chatId +
                         "&text=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}