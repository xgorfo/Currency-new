package com.example.currencytracking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CurrencyEntity {

    @Id
    private UUID id;
    private String name;
    private String baseCurrency;
    private String priceChangeRange;
    private String description;

    public CurrencyEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getPriceChangeRange() {
        return priceChangeRange;
    }

    public void setPriceChangeRange(String priceChangeRange) {
        this.priceChangeRange = priceChangeRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", priceChangeRange='" + priceChangeRange + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
