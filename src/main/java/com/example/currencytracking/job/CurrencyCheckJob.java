package com.example.currencytracking.job;

import com.example.currencytracking.entity.CurrencyEntity;
import com.example.currencytracking.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CurrencyCheckJob {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrencyCheckJob(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 3600000) // каждый час
    public void checkCurrencies() {
        try {
            String url = "https://www.cbr-xml-daily.ru/daily_json.js";
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response).get("Valute");

            List<CurrencyEntity> trackedCurrencies = currencyRepository.findAll();

            for (CurrencyEntity currency : trackedCurrencies) {
                JsonNode currencyNode = root.get(currency.getName());

                if (currencyNode != null) {
                    BigDecimal current = currencyNode.get("Value").decimalValue();
                    BigDecimal previous = currencyNode.get("Previous").decimalValue();

                    BigDecimal changePercent = current.subtract(previous)
                            .divide(previous, 4, BigDecimal.ROUND_HALF_UP)
                            .multiply(BigDecimal.valueOf(100));

                    if (isChangeSignificant(changePercent, currency.getPriceChangeRange())) {
                        System.out.println("🔔 Уведомление: " + currency.getDescription());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isChangeSignificant(BigDecimal change, String priceChangeRange) {
        try {
            String[] parts = priceChangeRange.replace("%", "").split("/");
            BigDecimal up = new BigDecimal(parts[0]);
            BigDecimal down = new BigDecimal(parts[1]);

            return change.compareTo(up) >= 0 || change.compareTo(down) <= 0;
        } catch (Exception e) {
            return false;
        }
    }
}
